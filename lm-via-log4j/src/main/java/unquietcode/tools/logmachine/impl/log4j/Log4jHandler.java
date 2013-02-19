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
		MDC.put(LogEvent.TOPICS_KEY, e.topics);
		Throwable cause = e.getCause();

		try {
		switch (e.getLevel()) {
			case ERROR:
				if (cause == null) {
					log.error(e.formattedMessage);
				} else {
					log.error(e.formattedMessage, cause);
				}
			break;

			case WARN:
				if (cause == null) {
					log.warn(e.formattedMessage);
				} else {
					log.warn(e.formattedMessage, cause);
				}
			break;

			case INFO:
				if (cause == null) {
					log.info(e.formattedMessage);
				} else {
					log.info(e.formattedMessage, cause);
				}
			break;

			case DEBUG:
				if (cause == null) {
					log.debug(e.formattedMessage);
				} else {
					log.debug(e.formattedMessage, cause);
				}
			break;

			case TRACE:
				if (cause == null) {
					log.trace(e.formattedMessage);
				} else {
					log.trace(e.formattedMessage, cause);
				}
			break;

			default:
			throw new LogMachineException("internal error");

		}} finally {
			MDC.remove(Switchboard.MDC_KEY);
			MDC.remove(LogEvent.TOPICS_KEY);
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
}
