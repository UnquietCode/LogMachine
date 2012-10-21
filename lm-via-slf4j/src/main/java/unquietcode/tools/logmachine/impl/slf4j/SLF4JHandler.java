package unquietcode.tools.logmachine.impl.slf4j;

import org.slf4j.Logger;
import org.slf4j.MDC;
import unquietcode.tools.logmachine.core.*;

import java.util.List;

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
		MDC.put(Switchboard.MDC_KEY, Switchboard.put(metadata));

		Object[] data;
		List<Object> replacements = e.getReplacements();
		if (e.getCause() != null) {
			data = replacements.toArray(new Object[replacements.size()+1]);
			data[data.length-1] = e.getCause();
		} else {
			data = replacements.toArray(new Object[replacements.size()]);
		}

		switch (e.getLevel()) {
			case ERROR:
				log.error(e.getMessage(), data);
			break;
			case WARN:
				log.warn(e.getMessage(), data);
			break;
			case INFO:
				log.info(e.getMessage(), data);
			break;
			case DEBUG:
				log.debug(e.getMessage(), data);
			break;
			case TRACE:
				log.trace(e.getMessage(), data);
			break;
			default:
				throw new LogMachineException("Unknown log level: "+e.getLevel());
		}
	}
}
