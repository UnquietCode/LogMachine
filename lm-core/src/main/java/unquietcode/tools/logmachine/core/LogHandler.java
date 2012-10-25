package unquietcode.tools.logmachine.core;

/**
 * Implementations of LogHandler are able to respond to new log events
 * as they occur. Generally, each framework wishing to interact would
 * require its own {@link LogHandler} implementation.
 *
 * @author Ben Fagin
 * @version 08-05-2012
 * @param <T> Logger type
 */
public interface LogHandler<T> {
	void logEvent(T logger, LogEvent e);
	Level getLevel(T logger);
	String getLoggerName(T logger);

	boolean isError(T logger);
	boolean isWarn(T logger);
	boolean isInfo(T logger);
	boolean isDebug(T logger);
	boolean isTrace(T logger);
}
