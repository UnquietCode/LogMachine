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

	public void error(String message, Throwable exception) {
		LogMachineGenerator.start(new LogMachineHelperImpl(this)).because(exception).error(message);
	}

	public void error(String message, Object...data) {
		LogMachineGenerator.start(new LogMachineHelperImpl(this)).error(message, data);
	}

	public void warn(String message, Throwable exception) {
		LogMachineGenerator.start(new LogMachineHelperImpl(this)).because(exception).warn(message);
	}

	public void warn(String message, Object...data) {
		LogMachineGenerator.start(new LogMachineHelperImpl(this)).warn(message, data);
	}

	public void info(String message, Throwable exception) {
		LogMachineGenerator.start(new LogMachineHelperImpl(this)).because(exception).info(message);
	}

	public void info(String message, Object...data) {
		LogMachineGenerator.start(new LogMachineHelperImpl(this)).info(message, data);
	}

	public void debug(String message, Throwable exception) {
		LogMachineGenerator.start(new LogMachineHelperImpl(this)).because(exception).debug(message);
	}

	public void debug(String message, Object...data) {
		LogMachineGenerator.start(new LogMachineHelperImpl(this)).debug(message, data);
	}

	public void trace(String message, Throwable exception) {
		LogMachineGenerator.start(new LogMachineHelperImpl(this)).because(exception).trace(message);
	}

	public void trace(String message, Object...data) {
		LogMachineGenerator.start(new LogMachineHelperImpl(this)).trace(message, data);
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