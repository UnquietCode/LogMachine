package unquietcode.tools.logmachine.core.formats;

import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.Switchboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ben Fagin
 * @version 10-21-2012
 */
public class JSONFormat implements Format {

	@Override
	public String format(LogEvent event) {
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

		List<Enum> groups = event.getGroups();
		if (groups != null && !groups.isEmpty()) {
			sb.append(", \"groups\": [");
			boolean first = true;

			for (Enum group : groups) {
				if (!first) { sb.append(", "); }
				else { first = false; }

				sb.append("\"").append(group.getDeclaringClass().getName()).append(".").append(group).append("\"");
			}

			sb.append("]");
		}

		if (event.getData() != null) {
			// merge MDC with event data, choosing MDC first
			Map<String, String> data = new HashMap<String, String>();
			data.putAll(event.getData());
			//data.putAll(event.getMDCPropertyMap());
			data.remove(Switchboard.MDC_KEY);

			sb.append(", \"data\": {");
			boolean first = true;

			for (Map.Entry<String, String> datum : data.entrySet()) {
				if (!first) {
					sb.append(", ");
				} else {
					first = false;
				}

				sb.append("\"").append(datum.getKey()).append("\": \"").append(datum.getValue()).append("\"");
			}

			sb.append("}");
		}

		return sb.append("}").toString();
	}

	private static void appendNotNull(String key, StringBuilder sb, String data) {
		if (data != null && !data.isEmpty()) {
			sb.append(", \"").append(key).append("\": \"").append(data).append("\"");
		}
	}
}
