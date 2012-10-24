package unquietcode.tools.logmachine.impl.jdk14;

import unquietcode.tools.logmachine.core.LogMachine;

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
}
