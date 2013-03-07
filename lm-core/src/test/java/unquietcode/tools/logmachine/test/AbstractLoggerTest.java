package unquietcode.tools.logmachine.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.core.appenders.PersistentLogAppender;
import unquietcode.tools.logmachine.impl.simple.SimpleLogger;

/**
 * @author Ben Fagin
 * @version 10-24-2012
 */
public abstract class AbstractLoggerTest {
	protected final LogMachine lm;
	private boolean initialized = false;

	protected AbstractLoggerTest() {
		lm = getLogMachine();
	}

	@Before
	public void _setup() {
		if (initialized) { return; }

		SimpleLogger logger = SimpleLogger.getLogger(getLoggerName());
		logger.addAppender(eventAppender);
		logger.setLevel(Level.TRACE);
		eventAppender.start();

		initialized = true;
	}

	@After
	public void cleanup() {
		getEventAppender().getAllEvents().clear();
	}

	protected final LogEvent getSingleEvent() {
		PersistentLogAppender appender = getEventAppender();
		Assert.assertEquals("expected one event", 1, appender.getAllEvents().size());
		LogEvent event = appender.getAllEvents().get(0);
		appender.getAllEvents().clear();
		return event;
	}

	private final PersistentLogAppender eventAppender = new PersistentLogAppender();

	public PersistentLogAppender getEventAppender() {
		return eventAppender;
	}

	protected abstract String getLoggerName();

	public abstract LogMachine getLogMachine();

	/**
	 * Provide the desired test level. defaults to {@link unquietcode.tools.logmachine.core.Level#DEBUG}
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
