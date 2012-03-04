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
	}

	

}
