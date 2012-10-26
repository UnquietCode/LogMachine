package unquietcode.tools.logmachine.impl.log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import unquietcode.tools.logmachine.core.*;

/**
 * @author Ben Fagin
 * @version 10-26-2012
 */
public class Log4jHandler implements LogHandler<Logger> {

	@Override
	public void logEvent(Logger log, LogEvent e) {
		MDC.put(Switchboard.MDC_KEY, Switchboard.put(e));
		Throwable cause = e.getCause();

		switch (e.getLevel()) {
			case ERROR:
				if (cause == null) {
					log.error(new DefferedToString(e));
				} else {
					log.error(new DefferedToString(e), cause);
				}
			break;

			case WARN:
				if (cause == null) {
					log.warn(new DefferedToString(e));
				} else {
					log.warn(new DefferedToString(e), cause);
				}
			break;

			case INFO:
				if (cause == null) {
					log.info(new DefferedToString(e));
				} else {
					log.info(new DefferedToString(e), cause);
				}
			break;

			case DEBUG:
				if (cause == null) {
					log.debug(new DefferedToString(e));
				} else {
					log.debug(new DefferedToString(e), cause);
				}
			break;

			case TRACE:
				if (cause == null) {
					log.trace(new DefferedToString(e));
				} else {
					log.trace(new DefferedToString(e), cause);
				}
			break;

			default:
			throw new LogMachineException("internal error");
		}
	}

	@Override
	public String getLoggerName(Logger logger) {
		return logger.getName();
	}

	@Override
	public Level getLevel(Logger logger) {
		return Log4jLevelTranslator.$.toLogMachine(logger.getLevel());
	}

	@Override
	public boolean isError(Logger log) {
		return log.isEnabledFor(org.apache.log4j.Level.ERROR) || log.isEnabledFor(org.apache.log4j.Level.FATAL);
	}

	@Override
	public boolean isWarn(Logger log) {
		return log.isEnabledFor(org.apache.log4j.Level.WARN);
	}

	@Override
	public boolean isInfo(Logger log) {
		return log.isInfoEnabled();
	}

	@Override
	public boolean isDebug(Logger log) {
		return log.isDebugEnabled();
	}

	@Override
	public boolean isTrace(Logger log) {
		return log.isTraceEnabled();
	}

	private static class DefferedToString {
		private final LogEvent e;

		DefferedToString(LogEvent e) {
			this.e = e;
		}

		@Override
		public String toString() {
			return e.getFormattedMessage();
		}

	}
}
