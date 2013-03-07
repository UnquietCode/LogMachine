package unquietcode.tools.logmachine.impl.simple;

import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.appenders.Appender;
import unquietcode.tools.logmachine.core.formats.Format;
import unquietcode.tools.logmachine.core.formats.PlaintextFormat;

/**
 * @author Ben Fagin
 * @version 10-24-2012
 */
public class SimpleAppender implements Appender {
	private Format format = new PlaintextFormat();


	@Override
	public void append(LogEvent event) {
		StringBuilder msg = format.format(event);

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

	@Override
	public void start() {
		// nothing
	}

	@Override
	public void stop() {
		// nothing
	}
}
