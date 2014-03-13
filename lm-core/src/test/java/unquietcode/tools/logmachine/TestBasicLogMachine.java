package unquietcode.tools.logmachine;

import org.junit.Test;
import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.core.topics.Topic;
import unquietcode.tools.logmachine.impl.simple.SimpleLogMachine;
import unquietcode.tools.logmachine.impl.simple.SimpleLogger;
import unquietcode.tools.logmachine.test.AbstractLoggerTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static unquietcode.tools.logmachine.TestBasicLogMachine.TestTopics.User;
import static unquietcode.tools.logmachine.core.topics.QuickTopics.CrudOperations.Create;
import static unquietcode.tools.logmachine.core.topics.QuickTopics.Databases.Postgres;

public class TestBasicLogMachine extends AbstractLoggerTest {

	enum TestTopics implements Topic {
		One, Two, Three, User
	}

	@Override
	protected String getLoggerName() {
		return TestBasicLogMachine.class.getName();
	}

	@Override
	public LogMachine getLogMachine() {
		SimpleLogger logger = SimpleLogger.getLogger(TestBasicLogMachine.class);
		logger.setLevel(Level.TRACE);

		return new SimpleLogMachine(logger);
	}

	@Test
	public void testBasicEvent() {
		log.info("Hello world.");

		LogEvent event = getSingleEvent();
		assertEquals("expected same log level", Level.INFO, event.getLevel());
		assertEquals("expected same log level", "Hello world.", event.getMessage());
	}

	@Test
	public void testLevels() {
		log.info("info");
		log.info().send("info");

		log.debug("debug");
		log.debug().send("debug");

		log.trace("trace");
		log.trace().send("trace");

		log.warn("warn");
		log.warn().send("warn");

		log.error("error");
		log.error().send("error");

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

		log.warn("Oh %^&%!", ex);

		LogEvent event = getSingleEvent();

		assertEquals("expected same log level", Level.WARN, event.getLevel());
		assertEquals("expected same log level", "Oh %^&%!", event.getMessage());
		assertTrue("", event.getCause() == ex);
	}

	@Test
	public void testBuilder() {
		ExceptionalException ex = new ExceptionalException("fail");

		log.because(ex).from("testBuilder()").with("k", "v").debug("Oh %^&%!");

		LogEvent event = getSingleEvent();

		assertEquals("expected same log level", Level.DEBUG, event.getLevel());
		assertEquals("expected same log level", "Oh %^&%!", event.getMessage());
		assertTrue(event.getCause() == ex);
		assertEquals("v", event.getData().get("k"));
		assertEquals("testBuilder()", event.getLocation());
	}

	@Test
	public void testMessageReplacement() {
		log.info("hello {}", "world");

		LogEvent event = getSingleEvent();
		assertEquals("hello {}", event.getMessage());
		assertEquals("hello world", event.getFormattedMessage());
	}

	@Test
	public void testMessageReplacementWithNulls() {
		log.with("greeting", (String) null).info("{:greeting} {:notExists} {}", (Object) null);

		LogEvent event = getSingleEvent();
		assertEquals("null {:notExists} null", event.getFormattedMessage());
	}

	@Test
	public void testMessageReplacementWithGroupNumber() {
		log.to(TestTopics.One, TestTopics.Two, TestTopics.Three)
		   .info("\"{~0}  { ~1 } { ~2} {~3 }\"");

		LogEvent event = getSingleEvent();
		assertEquals("\"{~0}  One Two Three\"", event.getFormattedMessage());
	}


	@Test
	public void testMessageReplacemtnWithArgumentNumber() {
		log.debug("1 : {1}, 2 : { 2 }, 3 : {  3}, 4 : {4 }, 5 : { 5}", 1, 2, 3, 4, new RuntimeException());

		LogEvent event = getSingleEvent();
		assertEquals("1 : 1, 2 : 2, 3 : 3, 4 : 4, 5 : {5}", event.getFormattedMessage());
	}

	@Test
	public void testMessageReplacementWithMissingGroupNumber() {
		log.to(TestTopics.One, TestTopics.Two).info("{~1} {~3} {~2}");

		LogEvent event = getSingleEvent();
		assertEquals("One {~3} Two", event.getFormattedMessage());
	}

	@Test
	public void testMessageReplacementWithAssignment() {
		log.info("Hi {@name}!", "Bob");

		LogEvent event = getSingleEvent();
		assertEquals("Hi Bob!", event.getFormattedMessage());
		assertEquals("Bob", event.getData().get("name"));
	}

	@Test(expected = IllegalStateException.class)
	public void test_Message_Replacement_With_Conflicting_Assignment() {
		String name1 = new String("Bob");
		String name2 = new String("Bob");
		assertTrue("What did you do???", name1 != name2);

		log.with("name", name1).info("{@name}", name2);
	}

	@Test(expected = IllegalStateException.class)
	public void test_Message_Replacement_With_Conflicting_Assignment__Same_Identity() {
		String name = "Bob";
		log.with("name", name).info("{@name}", name);
	}

	@Test
	public void test_Message_Replacement_With_Multiple_References__Assignment_First() {
		final int userID = 290;

		log.to(Postgres, User, Create)
		   .warn("Could not create user with id {@ id} because a user with id {: id} already exists.", userID);

		final String expected = "Could not create user with id "+userID+" because a user with id "+userID+" already exists.";
		assertEquals(expected, getSingleEvent().getFormattedMessage());
	}

	@Test
	public void test_Message_Replacement_With_Multiple_References__Assignment_Last() {
		final int userID = 290;

		log.to(Postgres, User, Create)
		   .warn("Could not create user with id {: id} because a user with id {@ id} already exists.", userID);

		final String expected = "Could not create user with id "+userID+" because a user with id "+userID+" already exists.";
		assertEquals(expected, getSingleEvent().getFormattedMessage());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDuplicateTopics() {
		log.to(TestTopics.One, TestTopics.Two, TestTopics.One).info("oops");
	}

	@Test
	public void testConditionalBuilder() {
		log.when(false).info().send("hi");
		assertEquals(0, getEventAppender().getAllEvents().size());

		log.when(null).info("yo");
		assertEquals(0, getEventAppender().getAllEvents().size());

		log.when(true).info("sup");
		assertEquals(1, getEventAppender().getAllEvents().size());
	}

	@Test
	public void testThatObjectsArePreserved() {
		final String test = new String("test!");

		log.with("one", 1)
		   .with("two", 2.0)
		   .with("three", "3")
		   .with("four", test)
		   .debug("");

		LogEvent event = getSingleEvent();

		// these get boxed, so at least check that they're equal
		assertEquals(1, event.getData().get("one"));
		assertEquals(2.0, event.getData().get("two"));

		// but for these, make sure the same reference was returned
		assertTrue("3" == event.getData().get("three"));
		assertTrue(test == event.getData().get("four"));
	}

	@Test
	public void testThatNullIsAllowedForLocationData() {
		log.from(null).info("blah");
	}
}
