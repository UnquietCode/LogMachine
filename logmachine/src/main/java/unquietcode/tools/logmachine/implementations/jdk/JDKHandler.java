package unquietcode.tools.logmachine.implementations.jdk;

import unquietcode.tools.logmachine.LogEvent;
import unquietcode.tools.logmachine.LogEventHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class JDKHandler implements LogEventHandler<Logger> {
	@Override
	public void logEvent(Logger logger, LogEvent e) {
		if (e.cause == null) {
			logger.log(convertLevel(e.level), createMessage(e));
		} else {
			logger.log(convertLevel(e.level), createMessage(e), e.cause);
		}
	}

	private static String createMessage(LogEvent event) {
		StringBuilder sb = new StringBuilder();

		// print groups
		if (!event.groups.isEmpty()) {
			boolean first = true;
			sb.append("[");

			for (Enum group : event.groups) {
				if (!first) {
					sb.append(" | ");
				} else {
					first = false;
				}

				sb.append(group);
			}

			sb.append("] ");
		}

		// print source
		if (event.source != null) {
			sb.append("(").append(event.source).append(") ");
		}

		// print data
		sb.append(event.message);

		// TODO formatted parameters turned into message

		return sb.toString();
	}

	private static Level convertLevel(unquietcode.tools.logmachine.Level level) {
		switch (level) {
			case ERROR:
				return Level.SEVERE;
			case WARN:
				return Level.WARNING;
			case INFO:
				return Level.INFO;
			case DEBUG:
				return Level.FINE;
			case TRACE:
				return Level.FINEST;
			default:
				throw new RuntimeException("Unknown logging level: "+level.toString());
		}
	}
}
