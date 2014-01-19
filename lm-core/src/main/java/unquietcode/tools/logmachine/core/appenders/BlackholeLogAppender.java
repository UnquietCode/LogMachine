package unquietcode.tools.logmachine.core.appenders;

import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LoggingComponent;

/**
 * An implementation of {@link unquietcode.tools.logmachine.core.appenders.Appender} which prints
 * the formatted message to nowhere. The point is that it attempts to print the message, which can
 * trigger exceptions which are deferred due to lazy initialization of event data.
 *
 * @author Ben Fagin
 * @version 2-19-2013
 */
public class BlackholeLogAppender implements LoggingComponent {

	@Override
	public void handle(LogEvent event) {
		event.getFormattedMessage();
		event.getCause();
	}
}
