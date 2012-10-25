package unquietcode.tools.logmachine;

import org.junit.Test;
import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.impl.simple.SimpleLogMachine;
import unquietcode.tools.logmachine.test.AbstractLoggerTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestBasicLogMachine extends AbstractLoggerTest {

	// it's not the same logger factory, so it's not going to the right place.
	// could change them to getters and setters, but then it starts to become ridic.

	private enum TestGroups {
		One, Two, Three
	}

	@Override
	protected String getLoggerName() {
		return TestBasicLogMachine.class.getName();
	}

	@Override
	public LogMachine getLogMachine() {
		return new SimpleLogMachine(TestBasicLogMachine.class);
	}

	@Override
	protected Level getLevel() {
		return Level.TRACE;
	}

	@Test
	public void testBasicEvent() {
		lm.info("Hello world.");
		LogEvent event = getSingleEvent();
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

		for (LogEvent event : getEventAppender().getAllEvents()) {
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
		LogEvent event = getSingleEvent();

		assertEquals("expected same log level", Level.WARN, event.getLevel());
		assertEquals("expected same log level", "Oh %^&%!", event.getMessage());
		assertTrue("", event.getCause() == ex);
	}

	@Test
	public void testBuilder() {
		ExceptionalException ex = new ExceptionalException("fail");
		lm.because(ex).from("testBuilder()").with("k", "v").debug("Oh %^&%!");
		LogEvent event = getSingleEvent();

		assertEquals("expected same log level", Level.DEBUG, event.getLevel());
		assertEquals("expected same log level", "Oh %^&%!", event.getMessage());
		assertTrue(event.getCause() == ex);
		assertEquals("v", event.getData().get("k"));
		assertEquals("testBuilder()", event.getLocation());
	}

	@Test
	public void testMessageReplacement() {
		lm.info("hello {}", "world");
		LogEvent event = getSingleEvent();
		assertEquals("hello {}", event.getMessage());
		assertEquals("hello world", event.getFormattedMessage());
	}

	@Test
	public void testMessageReplacementWithNulls() {
		lm.with("greeting", (String) null).info("{:greeting} {:notExists} {}", (Object) null);
		LogEvent event = getSingleEvent();
		assertEquals("null {:notExists} null", event.getFormattedMessage());
	}

	@Test
	public void testMessageReplacementWithGroupNumber() {
		lm.to(TestGroups.One, TestGroups.Two, TestGroups.Three)
		  .info("\"{~0}  { ~1 } { ~2} {~3 }\"");

		LogEvent event = getSingleEvent();
		assertEquals("\"One  One Two Three\"", event.getFormattedMessage());
	}

	@Test
	public void testMessageReplacementWithMissingGroupNumber() {
		lm.to(TestGroups.One, TestGroups.Two).info("{~1} { ~3} {~2}");
		LogEvent event = getSingleEvent();
		assertEquals("One {~3} Two", event.getFormattedMessage());
	}
}
