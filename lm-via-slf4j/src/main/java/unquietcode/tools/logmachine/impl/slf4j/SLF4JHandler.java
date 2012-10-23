package unquietcode.tools.logmachine.impl.slf4j;

import org.slf4j.Logger;
import org.slf4j.MDC;
import unquietcode.tools.logmachine.core.*;

import java.util.Arrays;

/**
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class SLF4JHandler implements LogEventHandler<Logger> {

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
				log.error(e.getFormattedMessage());
			}
			break;

			case WARN:
			if (log.isWarnEnabled()) {
				log.warn(e.getFormattedMessage());
			}
			break;

			case INFO:
			if (log.isInfoEnabled()) {
				log.info(e.getFormattedMessage());
			}
			break;

			case DEBUG:
			if (log.isDebugEnabled()) {
				log.debug(e.getFormattedMessage());
			}
			break;

			case TRACE:
			if (log.isTraceEnabled()) {
				log.trace(e.getFormattedMessage());
			}
			break;

			default:
			throw new LogMachineException("internal error");
		}
	}
}
