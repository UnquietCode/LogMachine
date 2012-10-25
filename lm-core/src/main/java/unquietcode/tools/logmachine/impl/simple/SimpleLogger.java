package unquietcode.tools.logmachine.impl.simple;

import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.appenders.Appender;

import java.lang.ref.WeakReference;
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
	private WeakReference<String> name;

	private SimpleLogger() { }

	public static synchronized SimpleLogger getLogger(String name) {
		if (LOGGERS.containsKey(name)) {
			return LOGGERS.get(name);
		} else {
			SimpleLogger logger = new SimpleLogger();
			logger.level = Level.INFO;
			logger.appenders.add(DEFAULT_APPENDER);
			logger.name = new WeakReference<String>(name);

			LOGGERS.put(name, logger);
			return logger;
		}
	}

	public static SimpleLogger getLogger(Class clazz) {
		return getLogger(checkNotNull(clazz).getName());
	}

	public String getName() {
		String s = name.get();
		return s == null ? "" : s;
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
