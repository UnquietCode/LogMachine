package unquietcode.tools.logmachine.impl.jdk14;

import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.Switchboard;
import unquietcode.tools.logmachine.core.appenders.Appender;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**

 * @author Ben Fagin
 * @version 10-21-2012
 */
public class JDKHandlerAdaptor extends Handler {
	private Appender appender;

	public void setAppender(Appender appender) {
		this.appender = appender;
	}

	@Override
	public void publish(LogRecord event) {
		if (appender == null) {
			return;
		}

		String lookupKey = "_"+event.getSequenceNumber();
		LogEvent _event = Switchboard.get(lookupKey);

		if (_event != null) {
			appender.append(_event);
		}
	}

	@Override
	public void flush() {
		// nothing
	}

	@Override
	public void close() throws SecurityException {
		appender.stop();
	}
}
