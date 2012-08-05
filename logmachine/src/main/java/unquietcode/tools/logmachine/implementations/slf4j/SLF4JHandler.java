package unquietcode.tools.logmachine.implementations.slf4j;

import org.slf4j.Logger;
import org.slf4j.MDC;
import unquietcode.tools.logmachine.*;
import unquietcode.tools.logmachine.Switchboard;

import java.util.Arrays;

/**
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class SLF4JHandler implements LogEventHandler<Logger> {

	@Override
	public void logEvent(Logger log, LogEvent e) {
		EventMetadata metadata = new EventMetadata();
		metadata.setGroups(e.groups);
		metadata.setSource(e.source);
		MDC.put(Switchboard.MDC_KEY, Switchboard.put(metadata));

		Object[] data;
		if (e.cause != null) {
			data = Arrays.copyOf(e.data, e.data.length+1);
			data[data.length-1] = e.cause;
		} else {
			data = e.data;
		}

		switch (e.level) {
			case ERROR:
				log.error(e.message, data);
			break;
			case WARN:
				log.warn(e.message, data);
			break;
			case INFO:
				log.info(e.message, data);
			break;
			case DEBUG:
				log.debug(e.message, data);
			break;
			case TRACE:
				log.trace(e.message, data);
			break;
			default:
				throw new RuntimeException("Unknown log level: "+e.level);
		}
	}
}
