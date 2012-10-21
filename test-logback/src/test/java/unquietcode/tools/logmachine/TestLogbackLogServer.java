package unquietcode.tools.logmachine;

import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 * LogMachine backed by SLF4J.
 *
 * @author Ben Fagin
 * @version 05-16-2012
 */
public class TestLogbackLogServer {
	private static LogMachine log = LogFactory.getInstance(LoggerFactory.getLogger(TestLogbackLogServer.class));

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
