package unquietcode.tools.logmachine.impl.jdk14;

import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LogEventHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class JDKHandler implements LogEventHandler<Logger> {

	@Override
	public void logEvent(Logger logger, LogEvent e) {
		if (e.getCause() == null) {
			logger.log(convertLevel(e.getLevel()), createMessage(e));
		} else {
			logger.log(convertLevel(e.getLevel()), createMessage(e), e.getCause());
		}
	}

	private static String createMessage(LogEvent event) {
		StringBuilder sb = new StringBuilder();

		// print groups
		if (!event.getGroups().isEmpty()) {
			boolean first = true;
			sb.append("[");

			for (Enum group : event.getGroups()) {
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
		if (event.getLocation() != null) {
			sb.append("(").append(event.getLevel()).append(") ");
		}

		// print data
		sb.append(event.getFormattedMessage());

		return sb.toString();
	}

	private static Level convertLevel(unquietcode.tools.logmachine.core.Level level) {
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
