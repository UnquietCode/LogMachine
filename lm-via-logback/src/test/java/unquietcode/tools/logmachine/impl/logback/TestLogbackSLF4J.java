package unquietcode.tools.logmachine.impl.logback;

import org.junit.Test;
import unquietcode.tools.logmachine.LogMachine;
import unquietcode.tools.logmachine.impl.slf4j.LogFactory;

/**
 * LogMachine backed by SLF4J.
 *
 * @author Ben Fagin
 * @version 05-16-2012
 */
public class TestLogbackSLF4J {
	private static LogMachine log = LogFactory.getLogMachine(TestLogbackSLF4J.class);

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
