package unquietcode.tools.logmachine.impl.slf4j;

import org.junit.Ignore;
import org.slf4j.Logger;
import unquietcode.tools.logmachine.core.LogMachine;

/**
 * @author Benjamin Fagin
 * @version 02-12-2012
 */
public class APICompilationTest {

	/*
		Should also be able to seamlessly replace the SLF4J API.
		This test is just a compilation sanity check.
	 */
	@Ignore("compilation only")
	public void testCompilationOfCompatibleAPI() {
		LogMachine lm = null;
		Logger log = null;
		RuntimeException ex = null;

		log.debug("hello");
		lm.debug("hello");

		log.debug("hello {}", "world");
		lm.debug("hello {}", "world");

		log.info("error", ex);
		lm.info("error", ex);

		log.isInfoEnabled();
		lm.isInfoEnabled();
		lm.isInfo();
	}

}
