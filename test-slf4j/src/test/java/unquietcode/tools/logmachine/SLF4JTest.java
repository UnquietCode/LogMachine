package unquietcode.tools.logmachine;

import org.junit.Test;

/**
 * @author Ben Fagin
 * @version 05-16-2012
 */
public class SLF4JTest {
	static LogMachine log = LogFactory.getSLF4JCompatibleLog(SLF4JTest.class);


	enum Color {
		Blue, Red, Yellow, Green
	}
	
	@Test
	public void simple() {
		log.from("method").debug("hi");
		log.to(Color.Red, Color.Blue).info("hello");
		log.info("hello {0}", "world");
		log.to(Color.Red, Color.Blue).from("basic()").info("greetings");
		log.because(new RuntimeException("oh no, not again")).error("goodbye!");
	}
}
