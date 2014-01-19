package unquietcode.tools.logmachine.core.formats;

import unquietcode.tools.logmachine.core.LogEvent;

/**
 * @author Ben Fagin
 * @version 10-21-2012
 */
public interface Formatter {
	StringBuilder format(LogEvent event);
}
