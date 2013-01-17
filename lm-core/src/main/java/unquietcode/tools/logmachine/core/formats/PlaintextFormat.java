package unquietcode.tools.logmachine.core.formats;

import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.LogEvent;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ben Fagin
 * @version 10-21-2012
 */
public class PlaintextFormat implements Format {
	private Level thresholdForStackTrace = Level.WARN;

	public void setThresholdForStackTrace(Level level) {
		this.thresholdForStackTrace = checkNotNull(level, "a valid level must be provided");
	}


	@Override
	public String format(LogEvent event) {
		StringBuilder sb = new StringBuilder();

		sb.append(event.getTimestamp())
			.append(" [").append(event.getThreadName())
			.append("] ").append(event.getLevel().toString())
			.append(" ").append(event.getLoggerName())
			.append(" - ")
		;

		boolean extraDivider = false;

		if (event.getGroups() != null && !event.getGroups().isEmpty()) {
			boolean first = true;
			sb.append("[");

			for (Enum group : event.getGroups()) {
				if (!first) { sb.append(" | ");	}
				else { first = false; }

				sb.append(group);
			}

			sb.append("] ");
			extraDivider = true;
		}

		if (event.getLocation() != null) {
			sb.append("{@").append(event.getLocation()).append("} ");
			extraDivider = true;
		}

		if (extraDivider) {
			sb.append("- ");
		}

		sb.append(event.getFormattedMessage());

		// stack trace
		if (event.getLevel().isCoarserOrEqual(thresholdForStackTrace)) {
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
		}

		return sb.toString();
	}
}
