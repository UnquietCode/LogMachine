package unquietcode.tools.logmachine.impl.simple;

import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LoggingComponent;
import unquietcode.tools.logmachine.core.formats.Formatter;
import unquietcode.tools.logmachine.core.formats.ShorterPlaintextFormatter;

/**
 * @author Ben Fagin
 * @version 10-24-2012
 */
public class SimpleAppender implements LoggingComponent {
	private Formatter formatter = new ShorterPlaintextFormatter();


	@Override
	public void handle(LogEvent event) {
		StringBuilder msg = formatter.format(event);
		msg.append("\n");

		switch (event.getLevel()) {
			case ERROR:
			case WARN:
				System.err.println(msg);
			break;

			case INFO:
			case DEBUG:
			case TRACE:
				System.out.println(msg);
			break;
		}
	}
}
