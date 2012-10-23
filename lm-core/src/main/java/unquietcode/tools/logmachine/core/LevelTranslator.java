package unquietcode.tools.logmachine.core;

/**
 * @author Ben Fagin
 * @version 10-22-2012
 */
public interface LevelTranslator<_FrameworkLevel> {
	_FrameworkLevel fromLogMachine(Level level);
	Level toLogMachine(_FrameworkLevel level);
}
