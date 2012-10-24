package unquietcode.tools.logmachine.impl.logback;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.junit.Before;
import org.slf4j.impl.StaticLoggerBinder;
import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.test.AbstractLoggerTest;

/**
 * @author Ben Fagin
 * @version 10-22-2012
 */
public abstract class AbstractLogbackTest extends AbstractLoggerTest {

	@Override
	@Before
	public void _setup() {
		StaticLoggerBinder loggerBinder = StaticLoggerBinder.getSingleton();
		LoggerContext loggerContext = (LoggerContext) loggerBinder.getLoggerFactory();

		LogbackAppenderAdaptor appender = new LogbackAppenderAdaptor();
		appender.setAppender(eventAppender);

		Logger logger = loggerContext.getLogger(getLoggerName());
		logger.addAppender(appender);
		logger.setLevel(LogbackLevelTranslator.$.fromLogMachine(Level.TRACE));
		appender.start();
	}
}
