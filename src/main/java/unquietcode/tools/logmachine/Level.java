package unquietcode.tools.logmachine;

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
	
}
