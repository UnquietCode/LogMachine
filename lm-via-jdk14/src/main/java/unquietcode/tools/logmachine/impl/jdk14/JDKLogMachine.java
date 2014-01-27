package unquietcode.tools.logmachine.impl.jdk14;

import unquietcode.tools.logmachine.core.LogMachine;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class JDKLogMachine extends LogMachine<Logger> {

	public JDKLogMachine(Logger log) {
		super(log, new JDKHandler());
	}

	public JDKLogMachine(Class clazz) {
		this(Logger.getLogger(checkNotNull(clazz).getName()));
	}

	public JDKLogMachine(String name) {
		this(Logger.getLogger(name));
	}

	// anonymous logger, useful as a standalone logging component
	public JDKLogMachine() {
		this(newAnonymousLogger());
	}

	static Logger newAnonymousLogger() {
		Logger logger = Logger.getAnonymousLogger();
		logger.setUseParentHandlers(false);
		logger.setLevel(Level.ALL);

		return logger;
	}
}
