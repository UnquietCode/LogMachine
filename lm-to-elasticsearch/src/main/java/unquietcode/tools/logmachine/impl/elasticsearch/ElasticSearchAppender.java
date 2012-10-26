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

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ben Fagin
 * @version 10-24-2012
 */
public class ElasticSearchAppender implements Appender {
	private static final LogstashFormat formatter = new LogstashFormat();
	private Client client;

	private List<InetSocketTransportAddress> servers; // the servers this client will connect to
	private String clusterName = "elasticsearch";     // cluster name, where es default is default
	private int batchSize = 10;                       // number of events to wait for (the trade-off is in lost events)
	private Level batchThreshold = Level.ERROR;       // log level which permits skipping the batch
	private String indexName = createIndexName();     // the name of the index to log events to
	private final Queue<LogEvent> queue = new ConcurrentLinkedQueue<LogEvent>();
	private boolean enabled = true;
	private final Object indexerLock = new Object();

	private static String createIndexName() {
		return new SimpleDateFormat("MM-dd-yyyy").format(new Date());
	}


	@Override
	public void append(LogEvent event) {
		if (!enabled) { return; }

		if (event.getLevel().isLesserOrEqual(batchThreshold)) {
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

	private void logSingleEvent(LogEvent event) {
		ListenableActionFuture<IndexResponse> future = createRequest(event).execute();

		try {
			future.actionGet();
		} catch (Exception ex) {
			System.err.println("error while saving a log event to elastic search");
			ex.printStackTrace(System.err);
		}
	}

	private IndexRequestBuilder createRequest(LogEvent event) {
		return client.prepareIndex(indexName, "lm")
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
		indexerLock.notify();
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

				int size = events.size();

				if (size == 0) {
//					try {
//						indexerLock.wait();
//					} catch (InterruptedException ex) {
//						break;
//					}
				} else if (size == 1) {
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

	public void setIndexName(String indexName) {
		this.indexName = checkNotNull(indexName);
	}
}
