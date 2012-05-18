package unquietcode.tools.logmachine;

import org.junit.Test;
import unquietcode.tools.logmachine.printers.LogMachineJSONPrinter;

/**
 * @author Ben Fagin (Nokia)
 * @version 03-04-2012
 */
public class JSON_T {
	enum Color {
		Red, Blue, Green
	}
	
	
	@Test
	public void basic() {
		LogMachinePrinter printer = new LogMachineJSONPrinter(null);
		LogMachine lm = new LogMachine(Level.DEBUG, printer);

		lm.from("method").because(new RuntimeException("oops")).debug("hi");
		lm.to(Color.Red, Color.Green).info("hello");
		lm.info("hello {0}", "world");
	}
}
