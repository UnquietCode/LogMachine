package unquietcode.tools.logmachine.impl.simple;

import unquietcode.tools.logmachine.core.LogMachine;

/**
 * Primarily for testing. No hierarchies.
 *
 * @author Ben Fagin
 * @version 10-23-2012
 */
public class SimpleLogMachine extends LogMachine<SimpleLogger> {
	private static final SimpleLogHandler HANDLER = new SimpleLogHandler();

	public SimpleLogMachine(SimpleLogger logger) {
		super(logger, HANDLER);
	}

	public SimpleLogMachine(String logger) {
		this(SimpleLogger.getLogger(logger));
	}

	public SimpleLogMachine(Class clazz) {
		this(SimpleLogger.getLogger(clazz));
	}
}
