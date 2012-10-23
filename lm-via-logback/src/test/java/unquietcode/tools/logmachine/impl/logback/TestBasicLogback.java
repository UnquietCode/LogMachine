package unquietcode.tools.logmachine.impl.logback;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unquietcode.tools.logmachine.test.AssertionStream;

/**
 * SLF4J without anything else.
 *
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class TestBasicLogback {
	private static final Logger log = LoggerFactory.getLogger(TestBasicLogback.class);

	@Test
	public void testAssertionStream() {
		AssertionStream stream = new AssertionStream();
		System.setOut(stream);
		log.info("Hello world.");

		stream.assertEquals("Hello world.\n", "expected exact message printed.");
	}
}
