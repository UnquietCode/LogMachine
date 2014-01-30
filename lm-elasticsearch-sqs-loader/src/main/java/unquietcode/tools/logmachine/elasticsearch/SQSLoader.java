package unquietcode.tools.logmachine.elasticsearch;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
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

/**
 * load an ES process
 * run this wit that local host and port
 * see what happens on the head _plugin
 *
 * @author Ben Fagin
 */
public class SQSLoader {

	public static void main(String[] args) throws Exception {

		// load aws stuff
		final AWSCredentials credentials
			= new BasicAWSCredentials("", "");

		final String queueURL = "";
		final AmazonSQS sqs = new AmazonSQSClient(credentials);

		// mapper
		final ObjectReader reader = new ObjectMapper().reader();

		// create an ES appender
		final InetSocketTransportAddress server = new InetSocketTransportAddress("localhost", 9300);
		final ElasticSearchAppender appender = new ElasticSearchAppender();
		appender.setBatchSize(3);
		appender.setServers(Arrays.asList(server));
		appender.start();

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
				JsonNode node = reader.readTree(json);
				ObjectNode root = (ObjectNode) node;

				System.out.println(root.toString());

				LogEvent event = convert(root);
				appender.handle(event);

				// done, kill it
				//sqs.deleteMessage(new DeleteMessageRequest(queueURL, message.getReceiptHandle()));
			}
		}
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
}
