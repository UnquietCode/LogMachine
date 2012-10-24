package unquietcode.tools.logmachine.impl.simple;

import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.appenders.Appender;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Flat. No hierarchies.
 *
 * @author Ben Fagin
 * @version 10-24-2012
 */
public class SimpleLogger {
	private static final Map<String, SimpleLogger> LOGGERS = new WeakHashMap<String, SimpleLogger>();
	private static final SimpleAppender DEFAULT_APPENDER = new SimpleAppender();

	private Level level;
	private final List<Appender> appenders = new ArrayList<Appender>();


	private SimpleLogger() { }

	public static synchronized SimpleLogger getLogger(String name) {
		if (LOGGERS.containsKey(name)) {
			return LOGGERS.get(name);
		} else {
			SimpleLogger logger = new SimpleLogger();
			logger.level = Level.INFO;
			logger.appenders.add(DEFAULT_APPENDER);

			LOGGERS.put(name, logger);
			return logger;
		}
	}

	public static SimpleLogger getLogger(Class clazz) {
		return getLogger(checkNotNull(clazz).getName());
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public void addAppender(Appender appender) {
		appenders.add(checkNotNull(appender));
	}

	public List<Appender> getAppenders() {
		return Collections.unmodifiableList(appenders);
	}
}
