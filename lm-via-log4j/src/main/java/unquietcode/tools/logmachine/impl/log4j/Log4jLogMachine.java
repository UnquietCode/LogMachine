package unquietcode.tools.logmachine.impl.log4j;

import org.apache.log4j.Logger;
import unquietcode.tools.logmachine.core.LogMachine;

/**
 * @author Ben Fagin
 * @version 10-26-2012
 */
public class Log4jLogMachine extends LogMachine<Logger> {
	private static final Log4jHandler HANDLER = new Log4jHandler();

	public Log4jLogMachine(Logger log) {
		super(log, HANDLER);
	}

	public Log4jLogMachine(Class clazz) {
		this(Logger.getLogger(clazz));
	}

	public Log4jLogMachine(String name) {
		this(Logger.getLogger(name));
	}
}
