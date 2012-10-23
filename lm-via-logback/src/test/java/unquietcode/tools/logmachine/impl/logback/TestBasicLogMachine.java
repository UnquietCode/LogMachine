package unquietcode.tools.logmachine.impl.logback;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.impl.slf4j.LogFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestBasicLogMachine extends AbstractLogbackTest {
	private static final Logger log = LoggerFactory.getLogger(TestBasicLogMachine.class);
	private static final LogMachine lm = LogFactory.getLogMachine(log);


	@Override
	protected String getLoggerName() {
		return TestBasicLogMachine.class.getName();
	}

	@Override
	protected Level getLevel() {
		return Level.TRACE;
	}

	@Test
	public void testBasicEvent() {
		lm.info("Hello world.");
		assertEquals("expected one event", 1, eventAppender.getAllEvents().size());

		LogEvent event = eventAppender.getAllEvents().get(0);
		assertEquals("expected same log level", Level.INFO, event.getLevel());
		assertEquals("expected same log level", "Hello world.", event.getMessage());
	}

	@Test
	public void testLevels() {
		lm.info("info");
		lm.info().send("info");
		lm.debug("debug");
		lm.debug().send("debug");
		lm.trace("trace");
		lm.trace().send("trace");
		lm.warn("warn");
		lm.warn().send("warn");
		lm.error("error");
		lm.error().send("error");

		Map<Level, Boolean> seenLevels = new HashMap<Level, Boolean>();
		Map<String, Integer> seenStrings = new HashMap<String, Integer>();

		for (LogEvent event : eventAppender.getAllEvents()) {
			seenLevels.put(event.getLevel(), true);
			String message = event.getMessage();

			Integer count = seenStrings.get(message);
			if (count == null ) count = 0;
			seenStrings.put(message, count+1);
		}

		assertEquals("5 strings", 5, seenStrings.size());
		assertEquals("5 levels", 5, seenLevels.size());

		for (Map.Entry<String, Integer> entry : seenStrings.entrySet()) {
			assertEquals("expected 2 of "+entry.getKey(), 2, (int) entry.getValue());
		}
	}

	@Test
	public void testWithInlineThrowable() {
		ExceptionalException ex = new ExceptionalException("fail");
		lm.warn("Oh %^&%!", ex);
		assertEquals("expected one event", 1, eventAppender.getAllEvents().size());
		LogEvent event = eventAppender.getAllEvents().get(0);

		assertEquals("expected same log level", Level.WARN, event.getLevel());
		assertEquals("expected same log level", "Oh %^&%!", event.getMessage());
		assertTrue("", event.getCause() == ex);
	}

	@Test
	public void testBuilder() {
		ExceptionalException ex = new ExceptionalException("fail");
		lm.because(ex).from("testBuilder()").with("k", "v").debug("Oh %^&%!");
		assertEquals("expected one event", 1, eventAppender.getAllEvents().size());
		LogEvent event = eventAppender.getAllEvents().get(0);

		assertEquals("expected same log level", Level.DEBUG, event.getLevel());
		assertEquals("expected same log level", "Oh %^&%!", event.getMessage());
		assertTrue(event.getCause() == ex);
		assertEquals("v", event.getData().get("k"));
		assertEquals("testBuilder()", event.getLocation());
	}

	@Test
	public void testMessageReplacement() {
		lm.info("hello {}", "world");
		LogEvent event = eventAppender.getAllEvents().get(0);
		assertEquals("hello {}", event.getMessage());
		assertEquals("hello world", event.getFormattedMessage());
	}
}
