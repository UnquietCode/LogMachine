package unquietcode.tools.logmachine.impl.log4j;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LoggingComponent;
import unquietcode.tools.logmachine.core.Switchboard;

/**
 * Implementation of {@link LoggingComponent} which is able to retrieve
 * log events via the log4j MDC. Events must be created through
 * a {@link unquietcode.tools.logmachine.core.LogMachine} instances.
 * Events logged directly to {@link org.apache.log4j.Logger} will be ignored.
 *
 * @author Ben Fagin
 * @version 10-26-2012
 */
public class Log4jAppenderAdapter extends AppenderSkeleton {
	private LoggingComponent component;

	public void setComponent(LoggingComponent component) {
		this.component = component;
	}

	@Override
	protected void append(LoggingEvent event) {
		if (component == null) {
			return;
		}

		String lookupKey = (String) event.getMDC(Switchboard.MDC_KEY);
		LogEvent _event = Switchboard.get(lookupKey);

		if (_event != null) {
			component.handle(_event);
		}
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

	@Override
	public void close() {
		// nothing
	}
}
