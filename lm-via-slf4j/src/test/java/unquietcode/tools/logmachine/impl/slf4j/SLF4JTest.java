package unquietcode.tools.logmachine.impl.slf4j;

import org.junit.Test;
import unquietcode.tools.logmachine.core.LogMachine;

/**
 * LogMachine backed by SLF4J.
 *
 * @author Ben Fagin
 * @version 05-16-2012
 */
public class SLF4JTest {
	private static LogMachine log = new SLF4JLogMachine(SLF4JTest.class);

	enum Color {
		Blue, Red, Yellow, Green
	}

	@Test
	public void simple() {
		log.from("method").debug("hi");
		log.to(Color.Red, Color.Blue).info("hello");
		log.info("hello {0}", "world");
		log.to(Color.Red, Color.Yellow).from("basic()").info("greetings");
		log.because(new RuntimeException("oh no, not again")).error("goodbye!");
	}
}
