package unquietcode.tools.logmachine.impl.slf4j;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unquietcode.tools.logmachine.test.AssertionStream;

import java.io.PrintStream;

/**
 * SLF4J without anything else.
 *
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class TestBasicSLF4J {
	private static final Logger logger = LoggerFactory.getLogger(TestBasicSLF4J.class.getName());
	private static final SLF4JLogMachine log = new SLF4JLogMachine(logger);

	@Test
	public void testAssertionStream() {
		final PrintStream stdErr = System.err;
		AssertionStream stream = new AssertionStream();
		System.setErr(stream);

		try {
			log.info("Hello world xoxo.");
			stream.assertContains("Hello world xoxo.", "expected exact message printed");
		} finally {
			System.setErr(stdErr);
		}
	}
}
