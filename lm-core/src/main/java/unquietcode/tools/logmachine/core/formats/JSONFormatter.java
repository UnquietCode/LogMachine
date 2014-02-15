package unquietcode.tools.logmachine.core.formats;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.Switchboard;
import unquietcode.tools.logmachine.core.topics.StringTopic;
import unquietcode.tools.logmachine.core.topics.Topic;

import java.io.IOException;
import java.util.*;

/**
 * @author Ben Fagin
 * @version 10-21-2012
 */
public class JSONFormatter implements Unformatter {
	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public StringBuilder format(LogEvent event) {
		final ObjectNode data = mapper.createObjectNode();

		// start, and all non-string properties
		data.put(DICTIONARY_TIMESTAMP, event.getTimestamp());
		data.put(DICTIONARY_LOGGERNAME, event.getLoggerName());

		appendNotNull(DICTIONARY_MESSAGE, data, event.getFormattedMessage());
		appendNotNull(DICTIONARY_THREAD_NAME, data, event.getThreadName());
		appendNotNull(DICTIONARY_LEVEL, data, event.getLevel());
		appendNotNull(DICTIONARY_LOCATION, data, event.getLocation());

		Throwable throwable = event.getCause();
		if (throwable != null) {
			ArrayNode causes = data.putArray(DICTIONARY_CAUSE);

			while (throwable != null) {
				ObjectNode cause = mapper.createObjectNode()
					.put(DICTIONARY_CAUSE_CLASS, throwable.getClass().getName())
					.put(DICTIONARY_CAUSE_MESSAGE, throwable.getMessage())
				;

				ArrayNode stacktrace = cause.putArray(DICTIONARY_CAUSE_STACKTRACE);

				for (StackTraceElement element : throwable.getStackTrace()) {
					stacktrace.add(element.toString());
				}

				causes.add(cause);
				throwable = throwable.getCause();
			}
		}

		List<Topic> _topics = event.getTopics();

		if (!_topics.isEmpty()) {
			ArrayNode topics = data.putArray(DICTIONARY_TOPICS);

			for (Topic topic : _topics) {
				topics.add(topic.name());
			}
		}

		// Event data
		// TODO clean up
		// merge MDC with event data, choosing MDC first
		Map<String, Object> eventData = new HashMap<String, Object>();
		eventData.putAll(event.getData());
		//data.putAll(event.getMDCPropertyMap());
		eventData.remove(Switchboard.MDC_KEY);

		ObjectNode dataField = data.putObject(DICTIONARY_DATA);

		for (Map.Entry<String, Object> entry : eventData.entrySet()) {
			dataField.putPOJO(entry.getKey(), entry.getValue());
		}

		try {
			String json = mapper.writeValueAsString(data);
			return new StringBuilder(json);
		} catch (IOException e) {
			throw new RuntimeException("could not serialize json", e);
		}
	}

	private static void appendNotNull(String key, ObjectNode obj, Object data) {
		if (data != null) {
			String s = data.toString();

			if (!s.isEmpty()) {
				obj.put(key, s);
			}
		}
	}

	@Override
	public LogEvent unformat(String json) {
		final JsonNode data;
		try {
			data = mapper.readTree(json);
		} catch (IOException e) {
			throw new RuntimeException("could not deserialize json", e);
		}

		// basic event information
		LogEvent event = new LogEvent();
			event.setTimestamp(data.get(DICTIONARY_TIMESTAMP).asLong());
			event.setMessage(data.get(DICTIONARY_MESSAGE).asText());
			event.setLevel(Level.valueOf(data.get(DICTIONARY_LEVEL).asText()));
			event.setLoggerName(data.get(DICTIONARY_LOGGERNAME).asText());
			event.setThreadName(data.get(DICTIONARY_THREAD_NAME).asText());

		// event topics
		if (data.has(DICTIONARY_TOPICS)) {
			ArrayNode topics = (ArrayNode) data.get(DICTIONARY_TOPICS);

			for (JsonNode topic : topics) {
				event.getTopics().add(new StringTopic(topic.asText()));
			}
		}

		// event data
		if (data.has(DICTIONARY_DATA)) {
			Iterator<Map.Entry<String, JsonNode>> eventData = data.get(DICTIONARY_DATA).fields();

			while (eventData.hasNext()) {
				Map.Entry<String, JsonNode> next = eventData.next();

				// TODO need to use raw json values (int decimal etc)
				event.getData().put(next.getKey(), next.getValue().toString());
			}
		}

		// exception data
		if (data.has(DICTIONARY_CAUSE)) {
			ArrayNode causes = (ArrayNode) data.get(DICTIONARY_CAUSE);
			Throwable exception = null;

			for (JsonNode _cause : causes) {
				ObjectNode cause = (ObjectNode) _cause;

				String clazz = cause.get(DICTIONARY_CAUSE_CLASS).asText();
				String message = cause.get(DICTIONARY_CAUSE_MESSAGE).asText();

				List<StackTraceElement> elements = new ArrayList<StackTraceElement>();
				ArrayNode stacktrace = (ArrayNode) cause.get(DICTIONARY_CAUSE_STACKTRACE);

				for (JsonNode node : stacktrace) {
					String text = node.asText();
					int lParen = text.indexOf('(');
					int rParent = text.indexOf(')');

					String classAndMethod = text.substring(0, lParen);
					int methodDot = classAndMethod.lastIndexOf('.');

					String className = classAndMethod.substring(0, methodDot);
					String methodName = classAndMethod.substring(methodDot+1);
					String location = text.substring(lParen+1, rParent-1);

					final StackTraceElement ste;

					if (location.equals("Native Method")) {
						ste = new StackTraceElement(className, methodName, null, -2);
					} else if (location.equals("Unknown Source")) {
						ste = new StackTraceElement(className, methodName, null, -1);
					} else if (location.contains(":")) {
						int split = location.indexOf(':');
						String fileName = location.substring(0, split);
						int line = Integer.parseInt(location.substring(split+1));

						ste = new StackTraceElement(className, methodName, fileName, line);
					} else {
						ste = new StackTraceElement(className, methodName, null, -1);
					}

					elements.add(ste);
				}

				StackTraceElement[] elementsArray = elements.toArray(new StackTraceElement[elements.size()]);

				// TODO we can't create the real exception reliably
				// (I guess this is one reason the other guys use IThrowableProxy...)
				exception = new DeserializedException(clazz, message, exception);
				exception.setStackTrace(elementsArray);
			}

			event.setCause(exception);
		}

		return event;
	}

	// fakeout
	public static class DeserializedException extends Throwable {
		private final String originalClass;

		DeserializedException(String originalClass, String message, Throwable cause) {
			super(message, cause);
			this.originalClass = originalClass;
		}

		public String getOriginalClass() {
			return originalClass;
		}
	}

	private static final String DICTIONARY_TIMESTAMP = "time";
	private static final String DICTIONARY_LOGGERNAME = "logger";
	private static final String DICTIONARY_MESSAGE = "message";
	private static final String DICTIONARY_THREAD_NAME = "thread";
	private static final String DICTIONARY_LEVEL = "level";
	private static final String DICTIONARY_LOCATION = "location";
	private static final String DICTIONARY_TOPICS = "topics";
	private static final String DICTIONARY_DATA = "data";

	private static final String DICTIONARY_CAUSE = "cause";
	private static final String DICTIONARY_CAUSE_STACKTRACE = "stacktrace";
	private static final String DICTIONARY_CAUSE_CLASS = "class";
	private static final String DICTIONARY_CAUSE_MESSAGE = "message";
}