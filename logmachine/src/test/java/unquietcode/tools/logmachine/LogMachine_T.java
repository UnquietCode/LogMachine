package unquietcode.tools.logmachine;

import org.junit.Test;
import unquietcode.tools.logmachine.printers.LogMachineConsolePrinter;

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
		LogMachinePrinter printer = new LogMachineConsolePrinter();
		LogMachine lm = new LogMachine(Level.DEBUG, printer);

		lm.from("method").debug("hi");
		lm.to(Color.Red, Color.Blue).info("hello");
		lm.info("hello {0}", "world");
		lm.because(new RuntimeException("oh no, not again")).error("goodbye!");
	}

	
	@Test
	public void configTest() {
		LogMachineConfiguration config = new LogMachineConfiguration();
		//config.mapPackage("unquietcode.tools.logmachine.LogMachine_T",
	}

}
