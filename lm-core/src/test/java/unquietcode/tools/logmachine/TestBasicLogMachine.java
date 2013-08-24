package unquietcode.tools.logmachine;

import org.junit.Test;
import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.core.topics.Topic;
import unquietcode.tools.logmachine.impl.simple.SimpleLogMachine;
import unquietcode.tools.logmachine.test.AbstractLoggerTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestBasicLogMachine extends AbstractLoggerTest {

	private enum TestGroups implements Topic {
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
		assertEquals("\"{~0}  One Two Three\"", event.getFormattedMessage());
	}

	@Test
	public void testMessageReplacementWithMissingGroupNumber() {
		lm.to(TestGroups.One, TestGroups.Two).info("{~1} {~3} {~2}");
		LogEvent event = getSingleEvent();
		assertEquals("One {~3} Two", event.getFormattedMessage());
	}

	@Test
	public void testMessageReplacementWithAssignment() {
		lm.info("Hi {@name}!", "Bob");
		LogEvent event = getSingleEvent();
		assertEquals("Hi Bob!", event.getFormattedMessage());
		assertEquals("Bob", event.getData().get("name"));
	}

	@Test(expected = IllegalStateException.class)
	public void testMessageReplacementWithConflictingAssignment() {
		String name = "Bob";
		lm.with("name", name).info("{@name}", name);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDuplicateTopics() {
		lm.to(TestGroups.One, TestGroups.Two, TestGroups.One).info("oops");
	}

	@Test
	public void testConditionalBuilder() {
		lm.when(false).info().send("hi");
		assertEquals(0, getEventAppender().getAllEvents().size());

		lm.when(null).info("yo");
		assertEquals(0, getEventAppender().getAllEvents().size());

		lm.when(true).info("sup");
		assertEquals(1, getEventAppender().getAllEvents().size());
	}

	@Test
	public void testThatObjectsArePreserved() {
		lm.with("one", 1)
		  .with("two", 2.0)
		  .with("three", "3")
		  .debug("");

		LogEvent event = getSingleEvent();
		assertEquals(1, event.getData().get("one"));
		assertEquals(2.0, event.getData().get("two"));
		assertEquals("3", event.getData().get("three"));
	}
}
