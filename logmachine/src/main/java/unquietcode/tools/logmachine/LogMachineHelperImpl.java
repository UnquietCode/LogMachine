package unquietcode.tools.logmachine;

import unquietcode.tools.logmachine.builder.LogMachineHelper;

public class LogMachineHelperImpl implements LogMachineHelper {
	private final LogMachine lm;
	private String _location;
	private Enum[] _categories;
	private Throwable _cause;
	
	public LogMachineHelperImpl(LogMachine lm) {
		this.lm = lm;
	}

	public void debug(String message, Object... data) {
	    lm._log(Level.DEBUG, message, data, _location, _cause, _categories);
    }

	public void warn(String message, Object... data) {
		lm._log(Level.WARN, message, data, _location, _cause, _categories);
    }

	public void info(String message, Object... data) {
		lm._log(Level.INFO, message, data, _location, _cause, _categories);
    }

	public void error(String message, Object... data) {
		lm._log(Level.ERROR, message, data, _location, _cause, _categories);
    }

	public void trace(String message, Object... data) {
		lm._log(Level.TRACE, message, data, _location, _cause, _categories);
    }

	public void from(String location) {
		_location = location;
    }

	public void to(Enum... categories) {
		_categories = categories;
    }

	public void because(Throwable cause) {
		_cause = cause;
    }
}
