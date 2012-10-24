package unquietcode.tools.logmachine.test;

import org.junit.Assert;
import org.junit.Before;
import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.appenders.PersistentLogAppender;
import unquietcode.tools.logmachine.impl.simple.SimpleLogger;

/**
 * @author Ben Fagin
 * @version 10-24-2012
 */
public abstract class AbstractLoggerTest {

	@Before
	public void _setup() {
		SimpleLogger logger = SimpleLogger.getLogger(getLoggerName());
		logger.addAppender(eventAppender);
		logger.setLevel(Level.TRACE);
		eventAppender.start();
	}

	protected final LogEvent getSingleEvent() {
		Assert.assertEquals("expected one event", 1, eventAppender.getAllEvents().size());
		LogEvent event = eventAppender.getAllEvents().get(0);
		eventAppender.getAllEvents().clear();
		return event;
	}

	protected final PersistentLogAppender eventAppender = new PersistentLogAppender();

	protected abstract String getLoggerName();

	/**
	 * Set the desired test level. defaults to {@link unquietcode.tools.logmachine.core.Level#DEBUG}
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
