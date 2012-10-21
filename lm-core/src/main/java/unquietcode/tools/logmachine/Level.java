package unquietcode.tools.logmachine;

import org.slf4j.Logger;

/**
 * @author Benjamin Fagin
 * @version 02-18-2012
 */
public enum Level {
	ERROR,
	WARN,
	INFO,
	DEBUG,
	TRACE
	
	;

	public boolean isLessThan(Level other) {
		return this != other && !this.isGreaterThan(other); 
	}

	/**
	 * Where 'greater' means finer grained.
	 *
	 * @param other
	 * @return
	 */
	public boolean isGreaterThan(Level other) {
		if (other == null) {
			return false;
		}

		switch (this) {
			case ERROR: return other != ERROR;
			case WARN:  return other != ERROR && other != WARN;
			case INFO:  return other == DEBUG || other == TRACE;
			case DEBUG: return other == TRACE;
			case TRACE: return false;
			default: throw new RuntimeException("Internal error.");
		}
	}
	
	public boolean isEqualTo(Level other) {
		return this == other;
	}

	//==o==o==o==o==o==o==| implementation helpers |==o==o==o==o==o==o==//

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
