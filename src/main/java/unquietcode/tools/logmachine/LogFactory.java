package unquietcode.tools.logmachine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unquietcode.tools.logmachine.printers.LogMachineSLF4JPrinter;

/**
 * @author Ben Fagin (Nokia)
 * @version 03-04-2012
 */
public class LogFactory {
	
	public static LogMachine getSLF4JCompatibleLog(Class forClass) {
		Logger logger = LoggerFactory.getLogger(forClass);
		LogMachinePrinter printer = new LogMachineSLF4JPrinter(logger);
		Level level = getLevelFromSLF4JLogger(logger);
		
		return new LogMachine(level, printer);
	}
	
	private static Level getLevelFromSLF4JLogger(Logger log) {
		if (log.isTraceEnabled()) {
			return Level.TRACE;
		}
		
		if (log.isDebugEnabled()) {
			return Level.DEBUG;
		}
		
		if (log.isInfoEnabled()) {
			return Level.INFO;
		}
		
		if (log.isWarnEnabled()) {
			return Level.WARN;
		}
		
		if (log.isErrorEnabled()) {
			return Level.ERROR;
		}
		
		throw new RuntimeException("Could not determined log level.");
	}
}
