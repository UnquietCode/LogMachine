package unquietcode.tools.logmachine.impl.jdk14;

import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LogHandler;
import unquietcode.tools.logmachine.core.Switchboard;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class JDKHandler implements LogHandler<Logger> {

	@Override
	public void logEvent(Logger logger, LogEvent e) {
		Level level = JDKLevelTranslator.$.fromLogMachine(e.getLevel());
		LogRecord lr = new LogRecord(level, e.getFormattedMessage());
		lr.setThrown(e.getCause());
		lr.setParameters(e.getReplacements());

		Switchboard.put(e, "_"+lr.getSequenceNumber());
		logger.log(lr);
	}

	@Override
	public String getLoggerName(Logger logger) {
		return logger.getName();
	}

	@Override
	public unquietcode.tools.logmachine.core.Level getLevel(Logger logger) {
		return JDKLevelTranslator.$.toLogMachine(logger.getLevel());
	}

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
}
