package unquietcode.tools.logmachine.impl.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unquietcode.tools.logmachine.core.ILogFactory;
import unquietcode.tools.logmachine.core.LogMachine;

/**
 * @author Ben Fagin
 * @version 10-21-2012
 */
public class LogFactory implements ILogFactory<Logger> {

	private LogFactory() { }

	public static LogMachine<Logger> getLogMachine(Class clazz) {
		Logger logger = LoggerFactory.getLogger(clazz);
		return new SLF4JLogMachine(logger);
	}

	public static LogMachine<Logger> getLogMachine(String name) {
		Logger logger = LoggerFactory.getLogger(name);
		return new SLF4JLogMachine(logger);
	}

	public static LogMachine<Logger> getLogMachine(Logger logger) {
		return new SLF4JLogMachine(logger);
	}

	@Override
	public LogMachine<Logger> getInstance(Logger logger) {
		throw new UnsupportedOperationException("private unimplemented");
	}
}
