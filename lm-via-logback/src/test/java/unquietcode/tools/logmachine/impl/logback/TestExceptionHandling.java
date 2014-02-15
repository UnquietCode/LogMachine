package unquietcode.tools.logmachine.impl.logback;

import org.junit.Test;
import unquietcode.tools.logmachine.core.LogEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Ben Fagin
 * @version 2014-02-14
 */
public class TestExceptionHandling extends AbstractLogbackTest {

	@Test
	public void test_that_basic_exception_handling_is_preserved() {
		final RuntimeException exception = new RuntimeException();
		log.info("what's up?", exception);

		LogEvent event = getSingleEvent();
		assertTrue(exception == event.getCause());
		assertEquals(0, event.getReplacements().length);
	}

	@Test
	public void test_that_formatted_exception_handling_is_preserved() {
		final RuntimeException exception = new RuntimeException();
		log.info("what's up? ({})", 15, exception);

		LogEvent event = getSingleEvent();
		assertTrue(exception == event.getCause());
		assertEquals("what's up? (15)", event.getFormattedMessage());
		assertEquals(1, event.getReplacements().length);
	}

	@Test
	public void test_exception_as_data() {
		final String message = "1-800-94-JENNY";

		final RuntimeException exception = new RuntimeException() {
			public @Override String toString() {
				return message;
			}
		};

		log.because(exception).info("what's up? => {}", exception);

		LogEvent event = getSingleEvent();
		assertTrue(exception == event.getCause());
		assertEquals(1, event.getReplacements().length);
		assertEquals("what's up? => "+message, event.getFormattedMessage());
	}
}
