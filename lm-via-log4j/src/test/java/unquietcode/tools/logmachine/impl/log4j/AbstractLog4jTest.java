package unquietcode.tools.logmachine.impl.log4j;

import org.apache.log4j.Logger;
import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.appenders.BlackholeLogAppender;
import unquietcode.tools.logmachine.test.AbstractLoggerTest;

/**
 * @author Ben Fagin
 * @version 10-26-2012
 */
public abstract class AbstractLog4jTest extends AbstractLoggerTest {
	private boolean initialized = false;

	@Override
	public void _setup() {
		if (initialized) { return; }

		Logger logger = Logger.getLogger(getLoggerName());
		logger.setLevel(Log4jLevelTranslator.$.fromLogMachine(Level.TRACE));

		// persistent appender
		Log4jAppenderAdapter appender = new Log4jAppenderAdapter();
		appender.setComponent(getEventAppender());
		logger.addAppender(appender);

		// non-printing appender
		appender = new Log4jAppenderAdapter();
		appender.setComponent(new BlackholeLogAppender());
		logger.addAppender(appender);

		initialized = true;
	}
}
