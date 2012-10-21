package unquietcode.tools.logmachine.impl.slf4j;

import org.junit.Test;
import org.slf4j.Logger;
import unquietcode.tools.logmachine.core.LogMachine;

/**
 * @author Benjamin Fagin
 * @version 02-12-2012
 */
public class LogMachine_T {
	enum Color {
		Blue, Red, Yellow, Green
	}
	
	@Test
	public void hi() {
		//LogMachinePrinter printer = new LogMachineConsolePrinter();
//		LogMachine lm = LogFactory.getLogMachine(java.util.logging.Logger.getLogger(LogMachine_T.class.getName()));
//
//		lm.from("method").debug("hi");
//		lm.to(Color.Red, Color.Blue).info("hello");
//		lm.info("hello {}", "world");
//		lm.because(new RuntimeException("oh no, not again")).error("goodbye!");
	}

	

	/*
		Should also be able to seamlessly replace an API, targeting primarily
		the existing SLF4J api's.
	 */

	@Test
	public void compatibleAPI() {
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
