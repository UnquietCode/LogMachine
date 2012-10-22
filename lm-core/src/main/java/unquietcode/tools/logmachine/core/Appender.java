package unquietcode.tools.logmachine.core;

/**
 * @author Ben Fagin
 * @version 10-21-2012
 */
public interface Appender {
	void append(LogEvent event);
	void start();
	void stop();
}
