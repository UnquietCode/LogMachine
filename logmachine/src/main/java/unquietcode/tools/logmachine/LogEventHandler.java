package unquietcode.tools.logmachine;

/**
 * @author Ben Fagin
 * @version 08-05-2012
 */
public interface LogEventHandler<T> {
	void logEvent(T logger, LogEvent e);
}
