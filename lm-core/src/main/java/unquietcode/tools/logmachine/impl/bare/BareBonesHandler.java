package unquietcode.tools.logmachine.impl.bare;

import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LogHandler;

/**
 * @author Ben Fagin
 * @version 2014-01-26
 */
public class BareBonesHandler implements LogHandler<BareBonesLogger> {

	@Override
	public void logEvent(BareBonesLogger logger, LogEvent e) {
		// nothing
	}

	@Override
	public String getLoggerName(BareBonesLogger logger) {
		return logger.getName();
	}

	@Override
	public Level getLevel(BareBonesLogger logger) {
		return logger.getLevel();
	}

	@Override
	public boolean isError(BareBonesLogger logger) {
		return logger.getLevel().isFinerOrEqual(Level.ERROR);
	}

	@Override
	public boolean isWarn(BareBonesLogger logger) {
		return logger.getLevel().isFinerOrEqual(Level.WARN);
	}

	@Override
	public boolean isInfo(BareBonesLogger logger) {
		return logger.getLevel().isFinerOrEqual(Level.INFO);
	}

	@Override
	public boolean isDebug(BareBonesLogger logger) {
		return logger.getLevel().isFinerOrEqual(Level.DEBUG);
	}

	@Override
	public boolean isTrace(BareBonesLogger logger) {
		return logger.getLevel().isFinerOrEqual(Level.TRACE);
	}
}