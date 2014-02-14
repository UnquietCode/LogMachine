package unquietcode.tools.logmachine;

import org.junit.Test;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.impl.simple.SimpleLogMachine;
import unquietcode.tools.logmachine.test.AbstractLoggerTest;

import static org.junit.Assert.assertEquals;

/**
 * IMPORTANT! Don't change anything in this class without
 * changing the one test.
 *
 * In fact, don't add any tests at all.
 *
 * In fact, don't even read this documentation. Stop it.
 * Right now. I mean it!
 *
 * @author Ben Fagin
 * @version 2014-01-26
 * @see {Stop reading!}
 */
public class TestAutomaticSourceDetection extends AbstractLoggerTest {

	protected String getLoggerName() {
		return TestAutomaticSourceDetection.class.getName();
	}

	@Override
	public LogMachine getLogMachine() {
		return new SimpleLogMachine(TestAutomaticSourceDetection.class);
	}

	@Test
	public void testAutomaticSource() {
		log.fromHere().info("Where you at dawg?"); // ---------- this line
		LogEvent event = getSingleEvent();        // -----------   goes
		// -----------------------------------------------------
		// ------------------------------------------------------- here:
		assertEquals("TestAutomaticSourceDetection#testAutomaticSource:37", event.getLocation());
	}
}