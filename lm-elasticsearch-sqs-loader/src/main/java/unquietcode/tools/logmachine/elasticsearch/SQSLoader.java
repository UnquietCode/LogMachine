package unquietcode.tools.logmachine.elasticsearch;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.topics.StringTopic;
import unquietcode.tools.logmachine.impl.elasticsearch.ElasticSearchAppender;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * load an ES process
 * run this wit that local host and port
 * see what happens on the head _plugin
 *
 * @author Ben Fagin
 */
public class SQSLoader implements Runnable {
	private final String queueURL;
	private final String accessKey;
	private final String secretKey;

	public SQSLoader(String queueURL, String accessKey, String secretKey) {
		this.accessKey = accessKey;
		this.queueURL = queueURL;
		this.secretKey = secretKey;
	}

	@Override
	public void run() {

		// load the sqs client
		final AmazonSQS sqs = getClient();

		// json mapper/unmapper
		final ObjectReader reader = new ObjectMapper().reader();

		// ES appender
		final InetSocketTransportAddress server = new InetSocketTransportAddress("localhost", 9300);
		final ElasticSearchAppender appender = new ElasticSearchAppender();
		appender.setBatchSize(3);
		appender.setServers(Arrays.asList(server));
		appender.start();


		// loop through and receive messages
		while (true) {
			ReceiveMessageRequest request = new ReceiveMessageRequest(queueURL)
				.withMaxNumberOfMessages(1)
				.withVisibilityTimeout(20)
			;

			ReceiveMessageResult response = sqs.receiveMessage(request);

			if (response.getMessages().isEmpty()) {
				System.out.println("\ndone\n\n");
				break;
			}

			for (Message message : response.getMessages()) {
				String json = message.getBody();

				final JsonNode node;
				try {
					node = reader.readTree(json);
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}

				ObjectNode root = (ObjectNode) node;
				System.out.println(root.toString());

				LogEvent event = convert(root);
				appender.handle(event);

				// done, kill it
				sqs.deleteMessage(new DeleteMessageRequest(queueURL, message.getReceiptHandle()));
			}
		}
	}

	private AmazonSQS getClient() {
		final AWSCredentialsProvider credentials;

		if (accessKey != null && secretKey != null) {
			BasicAWSCredentials _credentials = new BasicAWSCredentials(accessKey, secretKey);
			credentials = new StaticCredentialsProvider(_credentials);
		} else {
			credentials = new InstanceProfileCredentialsProvider();
		}

		return new AmazonSQSClient(credentials);
	}

	private static LogEvent convert(ObjectNode node) {
		LogEvent event = new LogEvent();

		event.setLoggerName(
			node.get("logger").textValue()
		);

		event.setLevel(
			Level.valueOf(node.get("level").textValue())
		);

		event.setMessage(
			node.get("message").textValue()
		);

		// topics
		JsonNode _topics = node.get("topics");
		ArrayNode topics = (ArrayNode) _topics;

		for (JsonNode _topic : topics) {
			StringTopic topic = new StringTopic(_topic.textValue());
			event.getGroups().add(topic);
		}

		// data
		Iterator<Map.Entry<String, JsonNode>> data = node.get("data").fields();

		while (data.hasNext()) {
			Map.Entry<String, JsonNode> next = data.next();

			// TODO need to use raw json values (int decimal etc)
			event.getData().put(next.getKey(), next.getValue().toString());
		}

		return event;
	}


	/**
	 * usage:  [queue url] [threads] (accessKey) (secretKey)
	 *
	 * When no access/secret keys are provided, then role-based
	 * authentication is used instead.
	 */
	public static void main(String[] args) throws Exception {
		int threadCount = Integer.parseInt(args[1]);
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(threadCount);

		String aKey, sKey, queue;

		if (args.length < 4) {
			queue = args[0];
			aKey = sKey = null;
		} else {
			queue = args[0];
			aKey = args[2];
			sKey = args[3];
		}

		for (int i=0; i < threadCount; ++i) {
			executor.scheduleWithFixedDelay(new SQSLoader(queue, aKey, sKey), 0, 15, TimeUnit.SECONDS);
		}

//		Thread.yield();
	}
}