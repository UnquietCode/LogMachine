package unquietcode.tools.logmachine.core.formats;

import unquietcode.tools.logmachine.core.EventMetadata;
import unquietcode.tools.logmachine.core.LogEvent;

/**
 * @author Ben Fagin
 * @version 10-21-2012
 */
public class PlaintextFormat implements Format {

	@Override
	public String format(LogEvent event) {
		StringBuilder sb = new StringBuilder();

		sb.append(event.getTimestamp())
			.append(" [").append(event.getThreadName())
			.append("] ").append(event.getLevel().toString())
			.append(" ").append(event.getLoggerName())
			.append(" - ")
		;

		EventMetadata metadata = event.getMetadata();
		boolean extraDivider = false;

		if (metadata.getGroups() != null && !metadata.getGroups().isEmpty()) {
			boolean first = true;
			sb.append("[");

			for (Enum group : metadata.getGroups()) {
				if (!first) { sb.append(" | ");	}
				else { first = false; }

				sb.append(group);
			}

			sb.append("] ");
			extraDivider = true;
		}

		if (metadata.getLocation() != null) {
			sb.append("{@").append(metadata.getLocation()).append("} ");
			extraDivider = true;
		}

		if (extraDivider) {
			sb.append("- ");
		}

		sb.append(event.getFormattedMessage());

		// stack trace
		Throwable throwable = event.getCause();
		if (throwable != null) {
			sb.append("\n").append(throwable.getClass().getName()).append(" - ").append(throwable.getMessage());

			for (StackTraceElement ste : throwable.getStackTrace()) {
				sb.append("\n\t").append(ste.toString());
			}

			Throwable cause = throwable.getCause();
			while (cause != null) {
				sb.append("\ncaused by ").append(cause.getClass().getName()).append(" - ").append(cause.getMessage());

				for (StackTraceElement ste : throwable.getStackTrace()) {
					sb.append("\n\t").append(ste.toString());
				}

				cause = cause.getCause();
			}
		}

		return sb.toString();
	}
}
