package unquietcode.tools.logmachine.impl.log4j;

import org.apache.log4j.Level;
import unquietcode.tools.logmachine.core.LevelTranslator;

/**
 * @author Ben Fagin
 * @version 10-26-2012
 */
public class Log4jLevelTranslator implements LevelTranslator<Level> {
	public static final Log4jLevelTranslator $ = new Log4jLevelTranslator();

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
		if (level == Level.ERROR)   return unquietcode.tools.logmachine.core.Level.ERROR;
		if (level == Level.WARN)    return unquietcode.tools.logmachine.core.Level.WARN;
		if (level == Level.INFO)    return unquietcode.tools.logmachine.core.Level.INFO;
		if (level == Level.DEBUG)   return unquietcode.tools.logmachine.core.Level.DEBUG;
		if (level == Level.TRACE)   return unquietcode.tools.logmachine.core.Level.TRACE;
		if (level == Level.FATAL)   return unquietcode.tools.logmachine.core.Level.ERROR;
		throw new RuntimeException("internal error");
	}
}
