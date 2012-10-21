package unquietcode.tools.logmachine;

import unquietcode.tools.logmachine.builder.GenericHelper;
import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LogMachine;

import java.util.Arrays;


public class GenericHelperImpl implements GenericHelper {
	private final LogMachine lm;
	private final LogEvent event = new LogEvent();

	public GenericHelperImpl(LogMachine lm) {
		this.lm = lm;
	}

	@Override
	public void debug(String message, Object...data) {
		event.setLevel(Level.DEBUG);
		event.setMessage(message);
	    lm._log(event);
    }

	@Override
	public void warn(String message, Object...data) {
		event.setLevel(Level.WARN);
		event.setMessage(message);
		lm._log(event);
    }

	@Override
	public void info(String message, Object...data) {
		event.setLevel(Level.INFO);
		event.setMessage(message);
		lm._log(event);
    }

	@Override
	public void error(String message, Object...data) {
		event.setLevel(Level.ERROR);
		event.setMessage(message);
		lm._log(event);
	}

	@Override
	public void trace(String message, Object...data) {
		event.setLevel(Level.TRACE);
		event.setMessage(message);
		lm._log(event);
	}

	@Override
	public void from(String location) {
		event.setLocation(location);
    }

	@Override
	public void to(Enum...categories) {
		if (categories == null) {
			categories = new Enum[]{null};
		}
		event.setGroups(Arrays.asList(categories));
    }

	@Override
	public void because(Throwable cause) {
		event.setCause(cause);
    }

	@Override
	public void with(String key, String value) {
		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null.");
		}

		event.getData().put(key, value);
	}

	@Override
	public void with(String key, Number value) {
		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null.");
		}

		event.getData().put(key, value == null ? null : value.toString());
	}
}