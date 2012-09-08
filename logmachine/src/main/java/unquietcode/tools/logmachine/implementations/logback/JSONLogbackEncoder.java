package unquietcode.tools.logmachine.implementations.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import unquietcode.tools.logmachine.EventMetadata;

/**
 * Encoder which creates a JSON representation of the logging event.
 *
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class JSONLogbackEncoder extends AbstractLogbackEncoder {

	@Override
	protected String doLayout(ILoggingEvent event, EventMetadata metadata) {
		StringBuilder sb = new StringBuilder();

		// start, and all non-string properties
		sb.append("{\"time\": ").append(event.getTimeStamp())
		  .append(",\"class\": \"").append(event.getLoggerName()).append("\"");

		appendNotNull("message", sb, event.getFormattedMessage());
		appendNotNull("source", sb, metadata.getSource());
		appendNotNull("thread", sb, event.getThreadName());
		appendNotNull("level", sb, event.getLevel().levelStr);

		IThrowableProxy throwable = event.getThrowableProxy();
		if (throwable != null) {
			sb.append(", \"cause\": [");
			boolean firstCause = true;

			while (throwable != null) {
				if (!firstCause) {
					sb.append(", ");
				} else {
					firstCause = false;
				}

			    sb.append("{\"class\": \"").append(throwable.getClassName())
			      .append("\", \"message\": \"").append(throwable.getMessage())
			      .append("\", \"stacktrace\": [");

				boolean first = true;
				for (StackTraceElementProxy element : throwable.getStackTraceElementProxyArray()) {
					if (!first) { sb.append(", "); }
					else { first = false; }

					sb.append("\"").append(element.getSTEAsString()).append("\"");
				}

				sb.append("]}");
				throwable = throwable.getCause();
			}

			sb.append("]");
		}

		if (metadata.getGroups() != null && !metadata.getGroups().isEmpty()) {
			sb.append(", \"groups\": [");
			boolean first = true;

			for (Enum group : metadata.getGroups()) {
				if (!first) { sb.append(", "); }
				else { first = false; }

				sb.append("\"").append(group.getDeclaringClass().getName()).append(".").append(group).append("\"");
			}

			sb.append("]");
		}

		return sb.append("}").toString();
	}

	private static void appendNotNull(String key, StringBuilder sb, String data) {
		if (data != null && !data.isEmpty()) {
			sb.append(", \"").append(key).append("\": \"").append(data).append("\"");
		}
	}
}
