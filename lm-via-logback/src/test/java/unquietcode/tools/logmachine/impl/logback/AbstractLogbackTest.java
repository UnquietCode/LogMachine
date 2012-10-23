package unquietcode.tools.logmachine.impl.logback;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.junit.Before;
import org.slf4j.impl.StaticLoggerBinder;
import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.appenders.PersistentLogAppender;

/**
 * @author Ben Fagin
 * @version 10-22-2012
 */
public abstract class AbstractLogbackTest {

	@Before
	public void _setup() {
		StaticLoggerBinder loggerBinder = StaticLoggerBinder.getSingleton();
		LoggerContext loggerContext = (LoggerContext) loggerBinder.getLoggerFactory();

		LogbackAppender appender = new LogbackAppender();
		appender.setAppender(eventAppender);

		Logger logger = loggerContext.getLogger(getLoggerName());
		logger.addAppender(appender);
		logger.setLevel(LogbackLevelTranslator.$.fromLogMachine(Level.TRACE));
		appender.start();
	}

	protected final PersistentLogAppender eventAppender = new PersistentLogAppender();

	protected abstract String getLoggerName();

	/**
	 * Set the desired test level. defaults to {@link Level#DEBUG}
	 */
	protected Level getLevel() {
		return Level.DEBUG;
	}

	protected static final class ExceptionalException extends RuntimeException {

		public ExceptionalException() {
			super();
		}

		public ExceptionalException(String message) {
			super(message);
		}

		public ExceptionalException(String message, Throwable cause) {
			super(message, cause);
		}

		public ExceptionalException(Throwable cause) {
			super(cause);
		}
	}
}
