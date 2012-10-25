package unquietcode.tools.logmachine.impl.slf4j;

import org.slf4j.Logger;
import org.slf4j.MDC;
import unquietcode.tools.logmachine.core.*;

import java.util.Arrays;

/**
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class SLF4JHandler implements LogHandler<Logger> {

	@Override
	public void logEvent(Logger log, LogEvent e) {
		EventMetadata metadata = new EventMetadata();
		metadata.setGroups(e.getGroups());
		metadata.setLocation(e.getLocation());
		metadata.setData(e.getData());
		MDC.put(Switchboard.MDC_KEY, Switchboard.put(e));

		Object[] data;
		Object[] replacements = e.getReplacements();

		if (e.getCause() != null) {
			data = Arrays.copyOf(replacements, replacements.length+1);
			data[data.length-1] = e.getCause();
		} else {
			data = replacements;
		}

		switch (e.getLevel()) {
			case ERROR:
			if (log.isErrorEnabled()) {
				log.error(e.getFormattedMessage(), data);
			}
			break;

			case WARN:
			if (log.isWarnEnabled()) {
				log.warn(e.getFormattedMessage(), data);
			}
			break;

			case INFO:
			if (log.isInfoEnabled()) {
				log.info(e.getFormattedMessage(), data);
			}
			break;

			case DEBUG:
			if (log.isDebugEnabled()) {
				log.debug(e.getFormattedMessage(), data);
			}
			break;

			case TRACE:
			if (log.isTraceEnabled()) {
				log.trace(e.getFormattedMessage(), data);
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
		return LevelTranslator.getLevelFromSLF4JLogger(logger);
	}

	@Override
	public boolean isError(Logger log) {
		return log.isErrorEnabled();
	}

	@Override
	public boolean isWarn(Logger log) {
		return log.isWarnEnabled();
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
