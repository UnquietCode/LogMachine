package unquietcode.tools.logmachine.impl.jdk14;

import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LogHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class JDKHandler implements LogHandler<Logger> {

	@Override
	public boolean isError(Logger log) {
		return log.isLoggable(Level.SEVERE);
	}

	@Override
	public boolean isWarn(Logger log) {
		return log.isLoggable(Level.WARNING);
	}

	@Override
	public boolean isInfo(Logger log) {
		return log.isLoggable(Level.INFO);
	}

	@Override
	public boolean isDebug(Logger log) {
		return log.isLoggable(Level.FINE);
	}

	@Override
	public boolean isTrace(Logger log) {
		return log.isLoggable(Level.FINEST) || log.isLoggable(Level.FINER);
	}

	@Override
	public void logEvent(Logger logger, LogEvent e) {
		if (e.getCause() == null) {
			logger.log(convertLevel(e.getLevel()), createMessage(e));
		} else {
			logger.log(convertLevel(e.getLevel()), createMessage(e), e.getCause());
		}
	}

//	TODO abstract or verify this method, then this handler should be good to go
// TODO move some of the tests to the lm-core package from logback
	
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
