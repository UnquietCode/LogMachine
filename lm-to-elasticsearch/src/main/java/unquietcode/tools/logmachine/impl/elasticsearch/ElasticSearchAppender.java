package unquietcode.tools.logmachine.impl.elasticsearch;

import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LogMachineException;
import unquietcode.tools.logmachine.core.appenders.Appender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Every incoming event goes on a queue.
 * A thread eats from it, consuming X at a time, then sleeping for Y.
 * On interrupt, shut down everything and die gracefully.
 *
 *
 * @author Ben Fagin
 * @version 10-24-2012
 */
public class ElasticSearchAppender implements Appender {
	private ElasticSearchJSONFormatter formatter = new LogstashFormatter_V1();
	private Client client;

	private List<InetSocketTransportAddress> servers; // the servers this client will connect to
	private String clusterName = "elasticsearch";     // cluster name, where ES's default is the default
	private int batchSize = 10;                       // number of events to wait for (the trade-off is in lost events)
	private Level batchThreshold = Level.ERROR;       // log level which permits skipping the batch (null := never)
	private final Queue<LogEvent> queue = new ConcurrentLinkedQueue<LogEvent>();
	private boolean enabled = true;

	@Override
	public void append(LogEvent event) {
		if (!enabled) { return; }

		if (event.getLevel().isCoarserOrEqual(batchThreshold)) {
			logSingleEvent(event);
		} else {
			queue.add(event);
		}
	}

	private void logMultipleEvents(List<LogEvent> events) {
		int retry = 2;

		while (!events.isEmpty() && retry > 0) {
			events = _logMultipleEvents(events);
			--retry;
		}

		if (!events.isEmpty()) {
			System.err.println("could not save all events");
		}
	}

	private void logSingleEvent(LogEvent event) {
		ListenableActionFuture<IndexResponse> future = createRequest(event).execute();

		try {
			future.actionGet();
		} catch (Exception ex) {
			System.err.println("error while saving a log event to elastic search");
			ex.printStackTrace(System.err);
		}
	}

	private List<LogEvent> _logMultipleEvents(List<LogEvent> events) {
		BulkRequestBuilder request = client.prepareBulk();

		for (LogEvent event : events) {
			request.add(createRequest(event));
		}

		List<LogEvent> retries = new ArrayList<LogEvent>();
		ListenableActionFuture<BulkResponse> future = request.execute();
		BulkResponse responses;

		try {
			responses = future.actionGet();
		} catch (Exception ex) {
			System.err.println("error while saving log events to elastic search");
			ex.printStackTrace(System.err);
			return Collections.emptyList();
		}

		for (BulkItemResponse response : responses) {
			if (!response.isFailed()) { continue; }
			System.err.println(response.getFailure().getMessage());

			retries.add(events.get(response.itemId()));
		}

		return retries;
	}

	private IndexRequestBuilder createRequest(LogEvent event) {
		return client.prepareIndex(formatter.getIndexName(event), "lm")
			.setSource(formatter.format(event))         // is there an ostream constructor?
			.setContentType(XContentType.JSON)
			.setTimestamp(Long.toString(event.getTimestamp()))
		;
	}

	@Override
	public void start() {
		if (servers == null || servers.isEmpty()) {
			throw new LogMachineException("At least one server address is required.");
		}

		Settings settings = ImmutableSettings.settingsBuilder()
			.put("cluster.name", clusterName)
			.put("client.transport.sniff", true)
		.build();

		TransportClient _client = new TransportClient(settings);
		client = _client;

		for (InetSocketTransportAddress server : servers) {
			_client.addTransportAddress(server);
		}

		Thread indexer = new Thread(new BatchIndexingJob());
		indexer.setDaemon(false);
		indexer.start();
	}

	@Override
	public void stop() {
		enabled = false;
	}

	/**
	 * To be called by the indexing thread after all work has completed.
	 */
	private void shutdown() {
		if (client != null) {
			client.close();
			client = null;
		}
	}

	private class BatchIndexingJob implements Runnable {
		public @Override void run() {
			while (enabled || !queue.isEmpty()) {
				List<LogEvent> events = new ArrayList<LogEvent>();

				for (int i=0; i < batchSize; ++i) {
					LogEvent event = queue.poll();

					if (event != null) { events.add(event); }
					else { break; }
				}

				if (events.size() == 0) {
					if (enabled) {
						try {
							Thread.sleep(500);
						} catch (InterruptedException ex) {
							// nothing
						}
					} else {
						break;
					}
				} else if (events.size() == 1) {
					logSingleEvent(events.get(0));
				} else {
					logMultipleEvents(events);
				}
			}

			// when the thread is about to poof out of existence, kill the client
			shutdown();
		}
	}

	public void setClusterName(String clusterName) {
		this.clusterName = checkNotNull(clusterName);
	}

	public void setServers(List<InetSocketTransportAddress> servers) {
		checkArgument(servers != null && !servers.isEmpty(), "a valid list of servers is required");
		this.servers = servers;
	}

	public void setBatchSize(int batchSize) {
		checkArgument(batchSize >= 0, "batch size must be >= 0");
		this.batchSize = batchSize;
	}

	public void setBatchThreshold(Level batchThreshold) {
		this.batchThreshold = checkNotNull(batchThreshold);
	}

	public void setFormatter(ElasticSearchJSONFormatter formatter) {
		this.formatter = checkNotNull(formatter);
	}
}
