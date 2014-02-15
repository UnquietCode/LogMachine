package unquietcode.tools.logmachine.impl.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.impl.StaticLoggerBinder;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.impl.slf4j.SLF4JLogMachine;
import unquietcode.tools.logmachine.test.AbstractLoggerTest;

/**
 * @author Ben Fagin
 * @version 10-22-2012
 */
public abstract class AbstractLogbackTest extends AbstractLoggerTest {
	private boolean initialized = false;

	@Override
	public LogMachine getLogMachine() {
		return new SLF4JLogMachine(getLogger());
	}

	@Override
	public void _setup() {
		if (initialized) { return; }

		LogbackAppenderAdapter appender = new LogbackAppenderAdapter();
		appender.setComponent(getEventAppender());

		Logger logger = getLogger();
		logger.addAppender(appender);
		logger.setLevel(Level.TRACE);

		appender.start();
		initialized = true;
	}

	private Logger getLogger() {
		StaticLoggerBinder loggerBinder = StaticLoggerBinder.getSingleton();
		LoggerContext loggerContext = (LoggerContext) loggerBinder.getLoggerFactory();
		return loggerContext.getLogger(getLoggerName());
	}
}
