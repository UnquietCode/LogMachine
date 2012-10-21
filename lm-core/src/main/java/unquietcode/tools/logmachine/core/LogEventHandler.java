package unquietcode.tools.logmachine.core;

/**
 * Implementations of LogEventHandler are able to respond to new log events
 * as they occur. Generally, each framework wishing to interact would
 * require its own {@link LogEventHandler} implementation.
 *
 * @author Ben Fagin
 * @version 08-05-2012
 * @param <T> Logger type
 */
public interface LogEventHandler<T> {
	void logEvent(T logger, LogEvent e);
}
