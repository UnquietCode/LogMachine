package unquietcode.tools.logmachine.impl.log4j;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.Test;
import unquietcode.tools.logmachine.test.AssertionStream;

import java.io.PrintStream;

/**
 * @author Ben Fagin
 * @version 10-26-12
 */
public class TestBasicLog4j {
	private static final Logger log = Logger.getLogger(TestBasicLog4j.class);

	@Test
	public void testAssertionStream() {
		AssertionStream stream = new AssertionStream();
		PrintStream out = System.out;
		System.setOut(stream);

		ConsoleAppender appender = new ConsoleAppender();
		appender.setLayout(new PatternLayout());
		log.addAppender(appender);
		appender.activateOptions();

		log.info("Hello world.");
		System.setOut(out);

		stream.assertEquals("Hello world.\n", "expected exact message printed");
	}
}
