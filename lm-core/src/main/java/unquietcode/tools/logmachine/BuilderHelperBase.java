package unquietcode.tools.logmachine;

import sun.misc.JavaLangAccess;
import sun.misc.SharedSecrets;
import unquietcode.tools.logmachine.core.BaseLogMachine;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LogMachine;

import java.util.Arrays;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


public abstract class BuilderHelperBase {
	private static final String logClassName = LogMachine.class.getName();

	protected final BaseLogMachine lm;
	protected final LogEvent event = new LogEvent();

	protected BuilderHelperBase(BaseLogMachine lm) {
		this.lm = lm;
		event.setLoggerName(lm.getLoggerName());
	}


	public void from(String location) {
		event.setLocation(location);
    }

	/**
	 * Copied with modifications from {@link java.util.logging.LogRecord#inferCaller}.
	 */
	public void from() {
		JavaLangAccess access = SharedSecrets.getJavaLangAccess();
		Throwable throwable = new Throwable();
		int depth = access.getStackTraceDepth(throwable);
		boolean lookingForLogger = true;

		for (int ix = 0; ix < depth; ix++) {
			// Calling getStackTraceElement directly prevents the VM
			// from paying the cost of building the entire stack frame.
			StackTraceElement frame = access.getStackTraceElement(throwable, ix);
			String cname = frame.getClassName();

			if (lookingForLogger) {
				// Skip all frames until we have found the first logger frame.
				if (cname.equals(logClassName)) {
					lookingForLogger = false;
				}
			} else {
				if (!cname.equals(logClassName)) {
					// We've found the relevant frame.
					String source = frame.getMethodName();
					from(source);
					return;
				}
			}
		}

		from("???");
	}

	public void to(Enum...categories) {
		if (categories == null) {
			categories = new Enum[]{null};

		// simple loop which checks for duplicates
		} else {
			for (int i=0; i < categories.length; ++i) {
				for (int j=i+1; j < categories.length; ++j) {
					if (categories[i] == categories[j]) {
						throw new IllegalArgumentException(
							"Two instances of topic '"+categories[i].name()+"' were provided."
						);
					}
				}
			}
		}

		event.setGroups(Arrays.asList(categories));
    }

	public void because(Throwable cause) {
		event.setCause(cause);
    }

	public void with(String key, String value) {
		checkNotNull(key, "key cannot be null");
		key = key.trim();
		checkArgument(!key.startsWith("~"), "~ is a reserved character at the start of an identifier");

		event.getData().put(key, value);
	}

	public void with(String key, Number value) {
		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null.");
		}

		event.getData().put(key, value == null ? null : value.toString());
	}
}