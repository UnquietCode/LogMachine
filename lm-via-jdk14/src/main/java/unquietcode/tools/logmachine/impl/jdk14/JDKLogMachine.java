package unquietcode.tools.logmachine.impl.jdk14;

import unquietcode.tools.logmachine.LogMachine;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class JDKLogMachine extends LogMachine<Logger> {
	private final Logger log;


	public JDKLogMachine(Logger log) {
		super(log, new JDKHandler());
		this.log = log;
	}

	@Override
	public boolean isError() {
		return log.isLoggable(Level.SEVERE);
	}

	@Override
	public boolean isWarn() {
		return log.isLoggable(Level.WARNING);
	}

	@Override
	public boolean isInfo() {
		return log.isLoggable(Level.INFO);
	}

	@Override
	public boolean isDebug() {
		return log.isLoggable(Level.FINE);
	}

	@Override
	public boolean isTrace() {
		return log.isLoggable(Level.FINEST);
	}
}
