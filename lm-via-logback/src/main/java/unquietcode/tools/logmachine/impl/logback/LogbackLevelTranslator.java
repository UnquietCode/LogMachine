package unquietcode.tools.logmachine.impl.logback;

import ch.qos.logback.classic.Level;
import unquietcode.tools.logmachine.core.LevelTranslator;

/**
 * @author Ben Fagin
 * @version 10-22-2012
 */
public class LogbackLevelTranslator implements LevelTranslator<Level> {

	public static final LogbackLevelTranslator $ = new LogbackLevelTranslator();


	@Override
	public Level fromLogMachine(unquietcode.tools.logmachine.core.Level level) {
		switch (level) {
			case ERROR: return Level.ERROR;
			case WARN:  return Level.WARN;
			case INFO:  return Level.INFO;
			case DEBUG: return Level.DEBUG;
			case TRACE: return Level.TRACE;
			default: throw new RuntimeException("internal error");
		}
	}

	@Override
	public unquietcode.tools.logmachine.core.Level toLogMachine(Level level) {
		switch (level.levelInt) {
			case Level.ERROR_INT:   return unquietcode.tools.logmachine.core.Level.ERROR;
			case Level.WARN_INT:    return unquietcode.tools.logmachine.core.Level.WARN;
			case Level.INFO_INT:    return unquietcode.tools.logmachine.core.Level.INFO;
			case Level.DEBUG_INT:   return unquietcode.tools.logmachine.core.Level.DEBUG;
			case Level.TRACE_INT:   return unquietcode.tools.logmachine.core.Level.TRACE;
			default: throw new RuntimeException("internal error");
		}
	}
}
