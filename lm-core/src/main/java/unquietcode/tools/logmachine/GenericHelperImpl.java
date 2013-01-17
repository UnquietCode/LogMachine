package unquietcode.tools.logmachine;

import unquietcode.tools.logmachine.builder.GenericHelper;
import unquietcode.tools.logmachine.core.BaseLogMachine;
import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.LogEvent;

import java.util.Arrays;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


public class GenericHelperImpl implements GenericHelper {
	private final BaseLogMachine lm;
	private final LogEvent event = new LogEvent();

	public GenericHelperImpl(BaseLogMachine lm) {
		this.lm = lm;
		event.setLoggerName(lm.getLoggerName());
	}

	@Override
	public void error(String message, Object...data) {
		logEvent(Level.ERROR, message, data);
	}

	@Override
	public void warn(String message, Object...data) {
		logEvent(Level.WARN, message, data);
	}

	@Override
	public void info(String message, Object...data) {
		logEvent(Level.INFO, message, data);
	}

	@Override
	public void debug(String message, Object...data) {
		logEvent(Level.DEBUG, message, data);
	}

	@Override
	public void trace(String message, Object...data) {
		logEvent(Level.TRACE, message, data);
	}

	private void logEvent(Level level, String message, Object[] data) {
		event.setLevel(level);
		event.setMessage(message);
		event.setReplacements(data);
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
		checkNotNull(key, "key cannot be null");
		key = key.trim();
		checkArgument(!key.startsWith("~"), "~ is a reserved character at the start of an identifier");

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