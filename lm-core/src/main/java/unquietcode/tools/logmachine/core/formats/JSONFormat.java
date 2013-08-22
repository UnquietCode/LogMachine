package unquietcode.tools.logmachine.core.formats;

import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.Switchboard;
import unquietcode.tools.logmachine.core.topics.Topic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ben Fagin
 * @version 10-21-2012
 */
public class JSONFormat implements Format {

	@Override
	public StringBuilder format(LogEvent event) {
		StringBuilder sb = new StringBuilder();

		// start, and all non-string properties
		sb.append("{\"time\": ").append(event.getTimestamp())
		  .append(",\"class\": \"").append(event.getLoggerName()).append("\"");

		appendNotNull("message", sb, event.getFormattedMessage());
		appendNotNull("thread", sb, event.getThreadName());
		appendNotNull("level", sb, event.getLevel().toString());

		Throwable throwable = event.getCause();
		if (throwable != null) {
			sb.append(", \"cause\": [");
			boolean firstCause = true;

			while (throwable != null) {
				if (!firstCause) {
					sb.append(", ");
				} else {
					firstCause = false;
				}

			    sb.append("{\"class\": \"").append(throwable.getClass().getName())
			      .append("\", \"message\": \"").append(throwable.getMessage())
			      .append("\", \"stacktrace\": [");

				boolean first = true;
				for (StackTraceElement element : throwable.getStackTrace()) {
					if (!first) { sb.append(", "); }
					else { first = false; }

					sb.append("\"").append(element.toString()).append("\"");
				}

				sb.append("]}");
				throwable = throwable.getCause();
			}

			sb.append("]");
		}

		appendNotNull("location", sb, event.getLocation());

		List<Topic> groups = event.getGroups();
		if (!groups.isEmpty()) {
			sb.append(", \"topics\": [");
			boolean first = true;

			for (Topic topic : groups) {
				if (!first) { sb.append(", "); }
				else { first = false; }

				sb.append("\"").append(topic.name()).append(".").append(topic).append("\"");
			}

			sb.append("]");
		}

		// Event data
		// TODO clean up
		// merge MDC with event data, choosing MDC first
		Map<String, Object> data = new HashMap<String, Object>();
		data.putAll(event.getData());
		//data.putAll(event.getMDCPropertyMap());
		data.remove(Switchboard.MDC_KEY);

		sb.append(", \"data\": ").append(getPropertiesJson(data));

		return sb.append("}");
	}

	private static String getPropertiesJson(Map<String, Object> data) {
		StringBuilder sb = new StringBuilder().append("{");
		boolean first = true;

		for (Map.Entry<String, Object> property : data.entrySet()) {
			if (first) {
				first = false;
			} else {
				sb.append(",");
			}

			sb.append("\"").append(property.getKey()).append("\":");
			Object value = property.getValue();

			if (value == null) {
				sb.append("null");
			} else if (Boolean.class.isAssignableFrom(value.getClass())) {
				sb.append(value);
			} else if (Number.class.isAssignableFrom(value.getClass())) {
				sb.append(value);
			} else {
				sb.append("\"").append(value.toString()).append("\"");
			}
		}

		return sb.append("}").toString();
	}

	private static void appendNotNull(String key, StringBuilder sb, String data) {
		if (data != null && !data.isEmpty()) {
			sb.append(", \"").append(key).append("\": \"").append(data).append("\"");
		}
	}
}
