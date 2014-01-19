package unquietcode.tools.logmachine.impl.jdk14;

import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LoggingComponent;
import unquietcode.tools.logmachine.core.Switchboard;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ben Fagin
 * @version 10-21-2012
 */
public class JDKHandlerAdapter extends Handler {
	private LoggingComponent component;

	public void setComponent(LoggingComponent component) {
		this.component = checkNotNull(component, "component cannot be null");
	}

	@Override
	public void publish(LogRecord event) {
		if (component == null) {
			return;
		}

		String lookupKey = "_"+event.getSequenceNumber();
		LogEvent _event = Switchboard.get(lookupKey);

		if (component != null) {
			component.handle(_event);
		}
	}

	@Override
	public void flush() {
		// nothing
	}

	@Override
	public void close() throws SecurityException {
		// nothing
	}
}
