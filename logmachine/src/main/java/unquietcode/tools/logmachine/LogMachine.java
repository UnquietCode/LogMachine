package unquietcode.tools.logmachine;


import unquietcode.tools.logmachine.builder.*;

import java.util.Arrays;

/**
 * @author Benjamin Fagin
 * @version 02-12-2012
 * @version 05-16-2012
 */
public abstract class LogMachine<T> implements LogMachineBuilder_because_from_to<Void> {
	private final LogEventHandler<T> handler;
	private final T logger;

	protected LogMachine(T logger, LogEventHandler<T> handler) {
		if (handler == null) {
			throw new IllegalArgumentException("Handler cannot be null.");
		}

		if (logger == null) {
			throw new IllegalArgumentException("Logger cannot be null.");
		}

		this.handler = handler;
		this.logger = logger;
	}

	/*private String _format(String message, Object...data) {
		if (data.length == 0) {
			return message;
		}

		ArrayIterator<Object> it = new ArrayIterator<Object>(data);
		return message.replaceAll("\\{(\\s*)([0-9]*)(\\s*)\\}", "$1"+it.get()+"$3");

		// TODO change this to an actual Matcher/Pattern implementation
	}

	class ArrayIterator<T> {
		int count = 0;
		T[] array;

		ArrayIterator(T[] array) {
			this.array = array;
		}

		public T get() {
			return array[count++];
		}

	}

	enum OrderBy {
		MESSAGE_ASCENDING, MESSAGE_DESCENDING,
		TIMESTAMP_ASCENDING, TIMESTAMP_DESCENDING,
		URI_ASCENDING, URI_DESCENDING,
	}*/

	//---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---//

	public abstract boolean isError();
	public boolean isErrorEnabled() {
		return isError();
	}

	public abstract boolean isWarn();
	public boolean isWarnEnabled() {
		return isWarn();
	}

	public abstract boolean isInfo();
	public boolean isInfoEnabled() {
		return isInfo();
	}

	public abstract boolean isDebug();
	public boolean isDebugEnabled() {
		return isDebug();
	}

	public abstract boolean isTrace();
	public boolean isTraceEnabled() {
		return isTrace();
	}

	//==o==o==o==o==o==o==| logging methods |==o==o==o==o==o==o==//

	void _log(Level level, String message, Object[] data, String source, Throwable cause, Enum[] categories) {
		if (categories == null) {
			categories = new Enum[]{};
		}

		if (data == null) {
			data = new Object[]{ null };
		}

		if (message == null) {
			message = "";
		}

		LogEvent event = new LogEvent(level, message, data, source, cause, Arrays.asList(categories));
		handler.logEvent(logger, event);
	}

	void _mark(String event, Enum[] categories) {
		throw new UnsupportedOperationException("not implemented");
	}

	void _mark(String event) {
		throw new UnsupportedOperationException("not implemented");
	}

	//==o==o==o==o==o==o==| builder methods |==o==o==o==o==o==o==//

	public Void error(String message, Throwable exception) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).because(exception).error(message);
	}

	public Void error(String message, Object...data) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).error(message, data);
	}

	public Void warn(String message, Throwable exception) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).because(exception).warn(message);
	}

	public Void warn(String message, Object...data) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).warn(message, data);
	}

	public Void info(String message, Throwable exception) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).because(exception).info(message);
	}

	public Void info(String message, Object...data) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).info(message, data);
	}

	public Void debug(String message, Throwable exception) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).because(exception).debug(message);
	}

	public Void debug(String message, Object...data) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).debug(message, data);
	}

	public Void trace(String message, Throwable exception) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).because(exception).trace(message);
	}

	public Void trace(String message, Object...data) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).trace(message, data);
	}

	public LogMachineBuilder_from_to<Void> because(Throwable cause) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).because(cause);
	}

	public LogMachineBuilder_because_to<Void> from(String location) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).from(location);
	}

	public LogMachineBuilder_because_from<Void> to(Enum... categories) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).to(categories);
	}
}