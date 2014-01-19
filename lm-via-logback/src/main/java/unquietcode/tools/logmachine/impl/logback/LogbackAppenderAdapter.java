package unquietcode.tools.logmachine.impl.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LoggingComponent;
import unquietcode.tools.logmachine.core.Switchboard;

/**
 * Native logback appender which is able to retrieve
 * log events via the logback MDC. Events must be created through
 * a {@link unquietcode.tools.logmachine.core.LogMachine} instances.
 * Events logged directly to {@link org.slf4j.Logger} will be ignored.
 *
 * @author Ben Fagin
 * @version 10-21-2012
 */
public class LogbackAppenderAdapter extends UnsynchronizedAppenderBase<ILoggingEvent> {
	private LoggingComponent component;

	public void setComponent(LoggingComponent component) {
		this.component = component;
	}

	@Override
	protected void append(ILoggingEvent event) {
		if (component == null) {
			return;
		}

		String lookupKey = event.getMDCPropertyMap().get(Switchboard.MDC_KEY);
		LogEvent _event = Switchboard.get(lookupKey);

		if (_event != null) {
			component.handle(_event);
		}
	}

	@Override
	public void start() {
		super.start();
	}

	@Override
	public void stop() {
		super.stop();
	}
}