package unquietcode.tools.logmachine.core.appenders;

import unquietcode.tools.logmachine.core.LogEvent;

/**
 * An implementation of {@link unquietcode.tools.logmachine.core.appenders.Appender} which prints
 * the formatted message to nowhere. The point is that it attempts to print the message, which can
 * trigger exceptions which are deferred due to lazy initialization of event data.
 *
 * @author Ben Fagin
 * @version 2-19-2013
 */
public class BlackholeLogAppender implements Appender {
	private boolean enabled = false;

	@Override
	public void append(LogEvent event) {
		if (enabled) {
			event.getFormattedMessage();
			event.getCause();
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
