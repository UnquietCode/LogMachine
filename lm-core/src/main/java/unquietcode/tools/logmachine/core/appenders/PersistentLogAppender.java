package unquietcode.tools.logmachine.core.appenders;

import unquietcode.tools.logmachine.core.LogEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of {@link Appender} which stores
 * all LogMachine {@link LogEvent} objects that it receives. This
 * is primarily useful for testing. Obviously, not a good idea to use this in a memory-sensitive
 * setting, but just in case you can always clear the list returned by {@link #getAllEvents()}
 *
 * @author Ben Fagin
 * @version 10-22-2012
 */
public class PersistentLogAppender implements Appender {

	private final List<LogEvent> events = new ArrayList<LogEvent>();
	private boolean enabled = false;

	/**
	 * Get the live list of events being logged to this appender.
	 *
	 * @return a live list of events
	 */
	public synchronized List<LogEvent> getAllEvents() {
		return events;
	}

	@Override
	public void append(LogEvent event) {
		if (enabled) {
			events.add(event);
		}
	}

	@Override
	public void start() {
		enabled = true;
	}

	@Override
	public void stop() {
		enabled = false;
	}

}
