package unquietcode.tools.logmachine.impl.simple;

import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LogHandler;
import unquietcode.tools.logmachine.core.LoggingComponent;

/**
 * @author Ben Fagin
 * @version 10-23-2012
 */
public class SimpleLogHandler implements LogHandler<SimpleLogger> {

	@Override
	public void logEvent(SimpleLogger logger, LogEvent e) {
		if (logger.getLevel().isFinerOrEqual(e.getLevel())) {
			for (LoggingComponent components : logger.getComponents()) {
				components.handle(e);
			}
		}
	}

	@Override
	public String getLoggerName(SimpleLogger logger) {
		return logger.getName();
	}

	@Override
	public Level getLevel(SimpleLogger logger) {
		return logger.getLevel();
	}

	@Override
	public boolean isError(SimpleLogger logger) {
		return logger.getLevel().isFinerOrEqual(Level.ERROR);
	}

	@Override
	public boolean isWarn(SimpleLogger logger) {
		return logger.getLevel().isFinerOrEqual(Level.WARN);
	}

	@Override
	public boolean isInfo(SimpleLogger logger) {
		return logger.getLevel().isFinerOrEqual(Level.INFO);
	}

	@Override
	public boolean isDebug(SimpleLogger logger) {
		return logger.getLevel().isFinerOrEqual(Level.DEBUG);
	}

	@Override
	public boolean isTrace(SimpleLogger logger) {
		return logger.getLevel().isFinerOrEqual(Level.TRACE);
	}
}
