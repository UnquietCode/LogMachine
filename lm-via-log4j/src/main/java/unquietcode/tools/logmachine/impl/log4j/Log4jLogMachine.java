package unquietcode.tools.logmachine.impl.log4j;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import unquietcode.tools.logmachine.core.LogMachine;

import java.util.Random;

/**
 * @author Ben Fagin
 * @version 10-26-2012
 */
public class Log4jLogMachine extends LogMachine<Logger> {
	private static final Log4jHandler HANDLER = new Log4jHandler();
	private static final Random random = new Random();

	public Log4jLogMachine(Logger log) {
		super(log, HANDLER);
	}

	public Log4jLogMachine(Class clazz) {
		this(Logger.getLogger(clazz));
	}

	public Log4jLogMachine(String name) {
		this(Logger.getLogger(name));
	}

	public Log4jLogMachine() {
		this(newAnonymousLogger());
	}

	static Logger newAnonymousLogger() {
		String loggerName = "anonymous" + random.nextInt(Integer.MAX_VALUE);
		Logger logger = Logger.getLogger(loggerName);
		logger.setAdditivity(false);
		logger.setLevel(Level.ALL);

		return logger;
	}
}
