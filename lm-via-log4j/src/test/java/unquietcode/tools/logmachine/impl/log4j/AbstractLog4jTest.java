package unquietcode.tools.logmachine.impl.log4j;

import org.apache.log4j.Logger;
import unquietcode.tools.logmachine.core.Level;
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

		Log4jAppender appender = new Log4jAppender();
		appender.setAppender(getEventAppender());

		Logger logger = Logger.getLogger(getLoggerName());
		logger.addAppender(appender);
		logger.setLevel(Log4jLevelTranslator.$.fromLogMachine(Level.TRACE));
		appender.start();

		initialized = true;
	}
}
