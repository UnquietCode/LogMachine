package unquietcode.tools.logmachine;

import java.util.Arrays;

/**
 * @author Benjamin Fagin
 * @version 02-12-2012
 */
public class LogMachine implements LogMachineBuilder.LogMachine_from_because_to {
	Level level;
	
	Level setLevel(Level level) {
		Level old = this.level;
		this.level = level;
		return old;
	}
	
	private void _log(Level level, String message, String source, Enum[] categories) {
		// TODO write the actual log entry

		Entry entry = new Entry(message, source, Arrays.asList(categories));
	}	
	
	private void _log(Level level, String message, Object[] data, String source, Enum[] categories) {
		if (level != this.level) {
			return;
		}
		
		if (data == null) {
			data = new Object[]{ null };
		}
		
		message = _format(message, data);
		_log(level, message, source, categories);
	}
	
	private void _log(Level level, String message, Object[] data, String source) {
		_log(level, message, data, source, new Enum[]{});	
	}

	private void _log(Level level, String message, Object[] data) {
		_log(level, message, data, null, new Enum[]{});
	}
	
	private String _format(String message, Object...data) {
		return null;
	}	
	
	//---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---//


	@Override
	public void debug(String message, Object...data) {
		_log(Level.DEBUG, message, data);
	}

	@Override
	public void warn(String message, Object...data) {
		_log(Level.WARN, message, data);
	}

	@Override
	public void info(String message, Object...data) {
		_log(Level.INFO, message, data);
	}

	@Override
	public void error(String message, Object...data) {
		_log(Level.ERROR, message, data);
	}

	@Override
	public void trace(String message, Object...data) {
		_log(Level.TRACE, message, data);
	}

	@Override
	public void mark(String event) {
		//nothing
	}

	@Override
	public void mark(String event, Enum...categories) {
		//nothing
	}

	@Override
	public LogMachine_because_to from(String location) {
		return null;
	}

	@Override
	public LogMachine_from_to because(Throwable throwable) {
		return null;
	}

	@Override
	public LogMachine_from_because to(Enum... categories) {
		return null;
	}


	enum OrderBy {
		MESSAGE_ASCENDING, MESSAGE_DESCENDING,
		TIMESTAMP_ASCENDING, TIMESTAMP_DESCENDING,
		URI_ASCENDING, URI_DESCENDING,
	}
}
