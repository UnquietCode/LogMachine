package unquietcode.tools.logmachine;

import unquietcode.tools.logmachine.implementations.jdk.JDKLogMachine;
import unquietcode.tools.logmachine.implementations.slf4j.SLF4JLogMachine;

/**
 * @author Ben Fagin (Nokia)
 * @version 03-04-2012
 */
public class LogFactory {
	//private static LogMachineConfiguration config;

	public static LogMachine getInstance(java.util.logging.Logger logger) {
		return new JDKLogMachine(logger);
	}

	public static LogMachine getInstance(org.slf4j.Logger logger) {
		return new SLF4JLogMachine(logger);
	}

	public static LogMachine getInstance(ch.qos.logback.classic.Logger logger) {
		// TODO logback implementation
		throw new UnsupportedOperationException("Not implemented.");
	}
}
