package unquietcode.tools.logmachine.impl.jdk14;

import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.appenders.PersistentLogAppender;

import java.util.logging.Logger;

/**
 * @author Ben Fagin
 * @version 10-22-2012
 */
public abstract class AbstractLoggerTest extends unquietcode.tools.logmachine.test.AbstractLoggerTest {
	private boolean initialized = false;

	@Override
	public void _setup() {
		if (initialized) { return; }

		JDKHandlerAdapter handler = new JDKHandlerAdapter();
		PersistentLogAppender appender = getEventAppender();
		handler.setComponent(appender);
		handler.setLevel(java.util.logging.Level.FINEST);

		Logger logger = Logger.getLogger(getLoggerName());
		logger.addHandler(handler);
		logger.setLevel(JDKLevelTranslator.$.fromLogMachine(Level.TRACE));

		initialized = true;
	}
}
