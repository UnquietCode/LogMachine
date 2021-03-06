package unquietcode.tools.logmachine;

import org.junit.Test;
import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.core.formats.ShorterPlaintextFormatter;
import unquietcode.tools.logmachine.core.topics.Topic;
import unquietcode.tools.logmachine.impl.simple.SimpleLogMachine;
import unquietcode.tools.logmachine.impl.simple.SimpleLogger;
import unquietcode.tools.logmachine.test.AbstractLoggerTest;

import static org.junit.Assert.assertEquals;

public class TestFormatters extends AbstractLoggerTest {
	private static final ShorterPlaintextFormatter shortFMT = new ShorterPlaintextFormatter();

	@Override
	protected String getLoggerName() {
		return TestFormatters.class.getName();
	}

	@Override
	public LogMachine getLogMachine() {
		SimpleLogger logger = SimpleLogger.getLogger(TestFormatters.class);
		logger.setLevel(Level.DEBUG);

		return new SimpleLogMachine(logger);
	}

	@Test
	public void testShortFormat_1() {
		log.debug("hi");
		LogEvent event = getSingleEvent();
		StringBuilder result = shortFMT.format(event);
		assertEquals("[main] DEBUG TestFormatters#testShortFormat_1:33\nhi", result.toString());
	}

	@Test
	public void testShortFormat_2() {
		log.to(TestTopics.One, TestTopics.Three).debug("hi");
		LogEvent event = getSingleEvent();
		StringBuilder result = shortFMT.format(event);
		assertEquals("[main] DEBUG TestFormatters#testShortFormat_2:41 --> [One | Three]\nhi", result.toString());
	}

	@Test
	public void testShortFormat_without_location() {
		log.from(null).info("blah");
		StringBuilder result = shortFMT.format(getSingleEvent());
		assertEquals("[main] INFO\nblah", result.toString());
	}

	private enum TestTopics implements Topic {
		One, Two, Three
	}
}
