package unquietcode.tools.logmachine;

import sun.misc.JavaLangAccess;
import sun.misc.SharedSecrets;
import unquietcode.tools.flapi.runtime.ExecutionListener;
import unquietcode.tools.logmachine.core.BaseLogMachine;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.core.topics.Topic;

import java.lang.reflect.Method;
import java.util.Arrays;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


public abstract class BuilderHelperBase {
	public final MethodSourceListener methodListener = new MethodSourceListener();

	protected final BaseLogMachine lm;
	protected final LogEvent event = new LogEvent();

	protected BuilderHelperBase(BaseLogMachine lm) {
		this.lm = lm;
		event.setLoggerName(lm.getLoggerName());
	}


	public void from(String location) {
		event.setLocation(location);
    }

	public void fromHere() {
		StackTraceElement frame = methodListener.getElement();

		// fail gracefully by allowing nulls
		if (frame == null) {
			from("???");
			return;
		}

		String className = frame.getClassName();

		if (className.contains(".")) {
			className = className.substring(className.lastIndexOf('.')+1);
		}

		String source = className+"#"+frame.getMethodName()+":"+frame.getLineNumber();
		from(source);
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

		event.getGroups().addAll(Arrays.asList(topics));
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
		checkNotNull(key, "key cannot be null");
		event.getData().put(key, value);
	}

	/**
	 * Listens for the fromHere() method to be called on the builder,
	 * and then grabs the caller information. This is done up front because
	 * it is difficult to find the caller after all of the proxy invocations
	 * (on the builder).
	 *
	 * TODO this code is very fragile
	 */
	public static class MethodSourceListener implements ExecutionListener {
		private StackTraceElement element;

		@Override
		public void next(Method method, Object[] args) {
			final boolean thisIsTheMethod
				= method.getName().equals("fromHere")
			   && method.getParameterTypes().length == 0
			;

			if (thisIsTheMethod) {

				// Copied with modifications from {@link java.util.logging.LogRecord#inferCaller}.
				JavaLangAccess access = SharedSecrets.getJavaLangAccess();
				Throwable throwable = new Throwable();
				element = access.getStackTraceElement(throwable, 3);

				// a little hack for when fromHere() is not the first call
				if (element.getClassName().equals(LogMachine.class.getName())) {
					element = access.getStackTraceElement(throwable, 4);
				}
			}
		}

		/*package*/ StackTraceElement getElement() {
			return element;
		}
	}
}