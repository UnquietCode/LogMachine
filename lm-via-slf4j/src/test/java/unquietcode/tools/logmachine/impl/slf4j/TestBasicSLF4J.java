package unquietcode.tools.logmachine.impl.slf4j;

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
public class TestBasicSLF4J {
	private static final Logger log = LoggerFactory.getLogger(TestBasicSLF4J.class.getName());

	@Test
	public void testAssertionStream() {
		AssertionStream stream = new AssertionStream();
		System.setErr(stream);

		log.info("Hello world xoxo.");
		stream.assertContains("Hello world xoxo.", "expected exact message printed");
	}
}
