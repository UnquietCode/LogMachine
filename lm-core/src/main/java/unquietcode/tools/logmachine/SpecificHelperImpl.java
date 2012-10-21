package unquietcode.tools.logmachine;

import unquietcode.tools.logmachine.builder.SpecificHelper;
import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LogMachine;

import java.util.Arrays;


public class SpecificHelperImpl implements SpecificHelper {
	private final LogMachine lm;
	private final LogEvent event = new LogEvent();

	public SpecificHelperImpl(LogMachine lm, Level level) {
		this.lm = lm;
		event.setLevel(level);
	}


	@Override
	public void send(String message, Object... data) {
		event.setMessage(message);

		// TODO data!

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