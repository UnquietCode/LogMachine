package unquietcode.tools.logmachine.core.formats;

import unquietcode.tools.logmachine.core.LogEvent;

/**
 * @author Ben Fagin
 * @version 10-21-2012
 */
public interface Format {
	String format(LogEvent event);
}
