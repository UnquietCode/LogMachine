package unquietcode.tools.logmachine.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.core.appenders.PersistentLogAppender;

/**
 * @author Ben Fagin
 * @version 10-24-2012
 */
public abstract class AbstractLoggerTest {
	protected final LogMachine log = getLogMachine();
	private boolean initialized = false;

	@Before
	public void _setup() {
		if (initialized) { return; }

		log.addComponent(eventAppender);

		initialized = true;
	}

	@After
	public void cleanup() {
		eventAppender.getAllEvents().clear();
	}

	protected final LogEvent getSingleEvent() {
		Assert.assertEquals("expected one event", 1, eventAppender.getAllEvents().size());
		LogEvent event = eventAppender.getAllEvents().get(0);
		eventAppender.getAllEvents().clear();

		return event;
	}

	private final PersistentLogAppender _defaultAppender = new PersistentLogAppender();
	private final PersistentLogAppender eventAppender = getEventAppender();

	// can override this still
	public PersistentLogAppender getEventAppender() {
		return _defaultAppender;
	}

	protected String getLoggerName() {
		return getClass().getName();
	}

	public abstract LogMachine getLogMachine();

	/**
	 * Provide the desired test level. defaults to {@link Level#DEBUG}
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
