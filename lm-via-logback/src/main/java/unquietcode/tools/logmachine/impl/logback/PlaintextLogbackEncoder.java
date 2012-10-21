package unquietcode.tools.logmachine.impl.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import unquietcode.tools.logmachine.core.EventMetadata;

/**
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class PlaintextLogbackEncoder extends AbstractLogbackEncoder {

	@Override
	protected String doLayout(ILoggingEvent event, EventMetadata metadata) {
		StringBuilder sb = new StringBuilder();

		sb.append(event.getTimeStamp())
		  .append(" [").append(event.getThreadName())
		  .append("] ").append(event.getLevel().levelStr)
	      .append(" ").append(event.getLoggerName())
		  .append(" - ")
		;

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
		IThrowableProxy throwable = event.getThrowableProxy();
		if (throwable != null) {
			sb.append("\n").append(throwable.getClassName()).append(" - ").append(throwable.getMessage());

			for (StackTraceElementProxy ste : throwable.getStackTraceElementProxyArray()) {
				sb.append("\n\t").append(ste.getSTEAsString());
			}

			IThrowableProxy cause = throwable.getCause();
			while (cause != null) {
				sb.append("\ncaused by ").append(cause.getClassName()).append(" - ").append(cause.getMessage());

				for (StackTraceElementProxy ste : throwable.getStackTraceElementProxyArray()) {
					sb.append("\n\t").append(ste.getSTEAsString());
				}

				cause = cause.getCause();
			}

		}

		return sb.toString();
	}
}
