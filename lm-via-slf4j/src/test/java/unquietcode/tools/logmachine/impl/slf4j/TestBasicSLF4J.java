package unquietcode.tools.logmachine.impl.slf4j;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SLF4J without anything else.
 *
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class TestBasicSLF4J {
	private static final Logger log = LoggerFactory.getLogger(TestBasicSLF4J.class);

	@Test
	public void test() {
		log.info("Hello world.");
	}
}
