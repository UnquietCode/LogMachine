package unquietcode.tools.logmachine.impl.jdk14;

import org.junit.Test;
import unquietcode.tools.logmachine.test.AssertionStream;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;


public class TestLogger {
	private static final Logger log = Logger.getLogger(TestLogger.class.getName());

	// tip: you have to setErr before you create the handler or it will still be on the old stream
	@Test
	public void testAssertionStream() {
		AssertionStream stream = new AssertionStream();
		System.setErr(stream);
		log.addHandler(new ConsoleHandler());

		log.info("Hello world xoxo.");
		stream.assertContains("Hello world xoxo.", "expected exact message printed.");
	}
}
