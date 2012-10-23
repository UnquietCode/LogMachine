package unquietcode.tools.logmachine.core.appenders;

import unquietcode.tools.logmachine.core.LogEvent;

/**
 * @author Ben Fagin
 * @version 10-21-2012
 */
public interface Appender {
	void append(LogEvent event);
	void start();
	void stop();
}
