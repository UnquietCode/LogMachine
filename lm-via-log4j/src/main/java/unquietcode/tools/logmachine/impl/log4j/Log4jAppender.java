package unquietcode.tools.logmachine.impl.log4j;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.Switchboard;
import unquietcode.tools.logmachine.core.appenders.Appender;

/**
 * Implementation of {@link unquietcode.tools.logmachine.core.appenders.Appender} which is able to retrieve
 * log events via the log4j MDC. Events must be created through
 * a {@link unquietcode.tools.logmachine.core.LogMachine} instances.
 * Events logged directly to {@link org.apache.log4j.Logger} will be ignored.
 *
 * @author Ben Fagin
 * @version 10-26-2012
 */
public class Log4jAppender extends AppenderSkeleton {
	private Appender appender;


	public void setAppender(Appender appender) {
		this.appender = appender;
	}

	@Override
	protected void append(LoggingEvent event) {
		if (appender == null) {
			return;
		}

		String lookupKey = (String) event.getMDC(Switchboard.MDC_KEY);
		LogEvent _event = Switchboard.get(lookupKey);

		if (_event != null) {
			appender.append(_event);
		}
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

	@Override
	public void close() {
		if (appender != null) {
			appender.stop();
		}
	}

	public void start() {
		if (appender != null) {
			appender.start();
		}
	}
}
