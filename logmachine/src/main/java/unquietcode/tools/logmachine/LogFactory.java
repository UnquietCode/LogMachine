package unquietcode.tools.logmachine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unquietcode.tools.logmachine.printers.LogMachineJSONPrinter;
import unquietcode.tools.logmachine.printers.LogMachineSLF4JPrinter;

import java.io.File;

/**
 * @author Ben Fagin (Nokia)
 * @version 03-04-2012
 */
public class LogFactory {
	private static LogMachineConfiguration config;
	private static final Class ROOT = DummyRoot.class;


	private static class DummyRoot { }
	
	// our two public methods for retrieving a log
	
	public static LogMachine getLog(Class forClass) {
		if (forClass == null) {
			forClass = ROOT;
		}
		
		return getLog(forClass.getName());
	}
	
	public static LogMachine getLog(String forPackage) {
		if (forPackage == null || (forPackage = forPackage.trim()).isEmpty()) {
			throw new IllegalArgumentException("Package name cannot be empty");
		}
		
		int dot;
		String front = forPackage;
		LogMachine lm;
		
		do {
			// try to get at the current package
			lm = getLogForPackage(front);
			if (lm != null) { break; }
			
			// switch to get the next highest package
			dot = front.lastIndexOf(".");
			if (dot != -1) {
				front = front.substring(0, dot);
			}
		} while (dot != -1);
		
		// default to the root logger
		if (lm == null) {
			lm = getLogForPackage(ROOT.getName());
		}
		
		return lm;
	}

	private static LogMachine getLogForPackage(String packageName) {
		// TODO
		return null;
	} 

	
	// specific for getting new log machine instances

	static LogMachine getJSONFileLog(File outputFile) {
		LogMachinePrinter printer = new LogMachineJSONPrinter(outputFile);
		
		// TODO rethink this hierarchy?

		/*
			
			package is mapped to a LogMachine instance
			LogMachine has a level set
			LogMachine has a printer, could be shared
			
			
		 */
		
		return null;
	}
	
	public static LogMachine getSLF4JCompatibleLog(Class forClass) {
		Logger logger = LoggerFactory.getLogger(forClass);
		LogMachinePrinter printer = new LogMachineSLF4JPrinter(logger);
		Level level = Level.getLevelFromSLF4JLogger(logger);
		
		return new LogMachine(level, printer);
	}
}
