package unquietcode.tools.logmachine.impl.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.Switchboard;
import unquietcode.tools.logmachine.core.appenders.Appender;

/**
 * Implementation of {@link Appender} which is able to retrieve
 * log events via the logback MDC. Events must be created through
 * a {@link unquietcode.tools.logmachine.core.LogMachine} instances.
 * Events logged directly to {@link org.slf4j.Logger} will be ignored.
 *
 * @author Ben Fagin
 * @version 10-21-2012
 */
public class LogbackAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
	private Appender appender;

	public void setAppender(Appender appender) {
		this.appender = appender;
	}

	@Override
	protected void append(ILoggingEvent event) {
		if (appender == null) {
			return;
		}

		String lookupKey = event.getMDCPropertyMap().get(Switchboard.MDC_KEY);
		LogEvent _event = Switchboard.get(lookupKey);

		if (_event != null) {
			appender.append(_event);
		}
	}

	@Override
	public void start() {
		appender.start();
		super.start();
	}

	@Override
	public void stop() {
		appender.stop();
		super.stop();
	}
}
