package unquietcode.tools.logmachine;

import unquietcode.tools.logmachine.core.BaseLogMachine;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.topics.Topic;

import java.util.Arrays;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


public abstract class BuilderHelperBase {
	protected final BaseLogMachine lm;
	protected final LogEvent event = new LogEvent();

	protected BuilderHelperBase(BaseLogMachine lm, Throwable locationHook) {
		this.lm = lm;
		event.setLoggerName(lm.getLoggerName());
		event.setLocation(getLocation(locationHook));
	}

	public void from(String location) {
		event.setLocation(location);
    }

	private static String getLocation(Throwable throwable) {
		StackTraceElement[] stackTrace = throwable.getStackTrace();

		if (stackTrace.length < 3) {
			return "???";
		}

		StackTraceElement frame = stackTrace[3];
		String className = frame.getClassName();

		if (className.contains(".")) {
			className = className.substring(className.lastIndexOf('.')+1);
		}

		return className+"#"+frame.getMethodName()+":"+frame.getLineNumber();
	}

	public void to(Topic...topics) {
		if (topics == null) {
			topics = new Topic[]{};
		}

		// simple loop which checks for duplicates
		for (int i=0; i < topics.length; ++i) {
			for (int j=i+1; j < topics.length; ++j) {
				if (topics[i] == topics[j]) {
					throw new IllegalArgumentException(
						"Two instances of topic '"+topics[i].name()+"' were provided."
					);
				}
			}
		}

		event.getTopics().addAll(Arrays.asList(topics));
    }

	public void because(Throwable cause) {
		event.setCause(cause);
    }

	public void with(String key, String value) {
		checkNotNull(key, "key cannot be null");
		key = key.trim();

		checkArgument(key.length() > 0, "empty keys are not allowed");
		checkArgument(Character.isLetter(key.charAt(0)), "keys must start with letters");

		event.getData().put(key, value);
	}

	public void with(String key, Number value) {
		checkNotNull(key, "key cannot be null");
		event.getData().put(key, value);
	}
}