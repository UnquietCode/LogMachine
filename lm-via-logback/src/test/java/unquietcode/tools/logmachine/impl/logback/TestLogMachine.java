package unquietcode.tools.logmachine.impl.logback;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.impl.slf4j.LogFactory;

/**
 * LogMachine backed by logback slf4j.
 *
 * @author Ben Fagin
 * @version 05-16-2012
 */
public class TestLogMachine {
	private static final Logger logger = LoggerFactory.getLogger(TestLogMachine.class);
	private static final LogMachine log = LogFactory.getLogMachine(TestLogMachine.class);

	// test that every log machine message is formatted correctly, hits the exisitng appenders
	// as well as one of our custom ones (configure the logging at the head of this test using logback api)
	// Also have a test which bybasses and uses the logger directly.



	enum Color {
		Blue, Red, Yellow, Green
	}

	@Test
	public void simple() {
		log.from("method").debug("hi");
		log.to(Color.Red, Color.Blue).info("hello");
		log.info("hello {0}", "world");
		log.to(Color.Red, Color.Yellow).from("basic()").info("greetings");
		log.because(new RuntimeException("oh no, not again", new NullPointerException("null pointer"))).error("goodbye!");
	}
}
