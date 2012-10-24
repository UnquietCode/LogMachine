package unquietcode.tools.logmachine.impl.slf4j;

import org.slf4j.Logger;
import unquietcode.tools.logmachine.core.Level;

/**
 * @author Ben Fagin
 * @version 10-21-2012
 * @deprecated will be removed if unused
 */
@Deprecated()
public class LevelTranslator {

	public static Level getLevelFromSLF4JLogger(Logger log) {
		if (log.isTraceEnabled()) {
			return Level.TRACE;
		}

		if (log.isDebugEnabled()) {
			return Level.DEBUG;
		}

		if (log.isInfoEnabled()) {
			return Level.INFO;
		}

		if (log.isWarnEnabled()) {
			return Level.WARN;
		}

		if (log.isErrorEnabled()) {
			return Level.ERROR;
		}

		throw new RuntimeException("Could not determined log level.");
	}
}
