package unquietcode.tools.logmachine.impl.jdk14;

import unquietcode.tools.logmachine.core.LevelTranslator;
import unquietcode.tools.logmachine.core.LogMachineException;

import java.util.logging.Level;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ben Fagin
 * @version 10-22-2012
 */
public class JDKLevelTranslator implements LevelTranslator<Level> {

	public static final JDKLevelTranslator $ = new JDKLevelTranslator();


	@Override
	public Level fromLogMachine(unquietcode.tools.logmachine.core.Level level) {
		checkNotNull(level, "level cannot be null");

		switch (level) {
			case ERROR: return Level.SEVERE;
			case WARN:  return Level.WARNING;
			case INFO:  return Level.INFO;
			case DEBUG: return Level.FINE;
			case TRACE: return Level.FINEST;
			default: throw new RuntimeException("internal error");
		}
	}

	@Override
	public unquietcode.tools.logmachine.core.Level toLogMachine(Level level) {
		checkNotNull(level, "level cannot be null");

		if (level == Level.SEVERE) {
			return unquietcode.tools.logmachine.core.Level.ERROR;
		} else if (level == Level.WARNING) {
			return unquietcode.tools.logmachine.core.Level.WARN;
		} else if (level == Level.INFO) {
			return unquietcode.tools.logmachine.core.Level.INFO;
		} else if (level == Level.FINE) {
			return unquietcode.tools.logmachine.core.Level.DEBUG;
		} else if (level == Level.FINER || level == Level.FINEST || level == Level.ALL) {
			return unquietcode.tools.logmachine.core.Level.TRACE;
		} else {
			throw new LogMachineException("internal error");
		}
	}
}
