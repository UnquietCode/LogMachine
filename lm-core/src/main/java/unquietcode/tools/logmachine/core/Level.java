package unquietcode.tools.logmachine.core;


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

	/**
	 * Where 'lesser' means coarser grained.
	 *
	 * @param other the other guy
	 * @return true if true, false if false
	 */
	public boolean isLesserOrEqual(Level other) {
		return isEqualTo(other) || isLessThan(other);
	}

	/**
	 * Where 'greater' means finer grained
	 *
	 * @param other the other girl
	 * @return true if potato, false otherwise
	 */
	public boolean isGreaterOrEqual(Level other) {
		if (other == null) {
			return false;
		}

		switch (this) {
			case ERROR: return other == ERROR;
			case WARN:  return other == WARN || other == ERROR;
			case INFO:  return other != DEBUG && other != TRACE;
			case DEBUG: return other != TRACE;
			case TRACE: return true;
			default: throw new RuntimeException("Internal error.");
		}
	}
	
	public boolean isEqualTo(Level other) {
		return this == other;
	}

	public boolean isGreaterThan(Level other) {
		return !isEqualTo(other) && isGreaterOrEqual(other);
	}

	public boolean isLessThan(Level other) {
		return !isGreaterOrEqual(other);
	}
}
