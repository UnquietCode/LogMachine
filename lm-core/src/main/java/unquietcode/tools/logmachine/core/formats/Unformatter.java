package unquietcode.tools.logmachine.core.formats;

import unquietcode.tools.logmachine.core.LogEvent;

/**
 * A reversible formatter.
 *
 * @author Ben Fagin
 * @version 2014-01-31
 */
public interface Unformatter extends Formatter {
	LogEvent unformat(String data);
}