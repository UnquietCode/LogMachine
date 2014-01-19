package unquietcode.tools.logmachine.core.appenders;

import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LoggingComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of {@link LoggingComponent} which stores
 * all LogMachine {@link LogEvent} objects that it receives. This
 * is primarily useful for testing. Obviously, not a good idea to use this in a memory-sensitive
 * setting, but just in case you can always clear the list returned by {@link #getAllEvents()}
 *
 * @author Ben Fagin
 * @version 10-22-2012
 */
public class PersistentLogAppender implements LoggingComponent {

	private final List<LogEvent> events = new ArrayList<LogEvent>();

	/**
	 * Get the live list of events being logged to this appender.
	 *
	 * @return a live list of events
	 */
	public synchronized List<LogEvent> getAllEvents() {
		return events;
	}

	@Override
	public void handle(LogEvent event) {
		events.add(event);
	}
}
