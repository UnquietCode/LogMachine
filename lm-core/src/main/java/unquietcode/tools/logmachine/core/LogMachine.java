package unquietcode.tools.logmachine.core;


import unquietcode.tools.logmachine.GenericHelperImpl;
import unquietcode.tools.logmachine.SpecificHelperImpl;
import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.*;
import unquietcode.tools.logmachine.builder.specific.SpecificLogMachine.SpecificLogMachineBuilder;
import unquietcode.tools.logmachine.builder.specific.SpecificLogMachine.SpecificLogMachineGenerator;
import unquietcode.tools.logmachine.builder.specific.SpecificLogMachine.SpecificLogMachineHelper;
import unquietcode.tools.logmachine.core.topics.Topic;

import java.lang.reflect.Proxy;

/**
 * @author Benjamin Fagin
 * @version 10-21-2012
 */
public abstract class LogMachine<T> extends BaseLogMachine<T> implements LogMachineBuilders_when<T> {

	protected LogMachine(T logger, LogHandler<T> handler) {
		super(logger, handler);
	}

	//==o==o==o==o==o==o==| logging methods |==o==o==o==o==o==o==//

	// specific one-shots (SLF4J style)

	@Override
	public void error(String message, Throwable exception) {
		if (isError()) {
			GenericLogMachineGenerator.start(new GenericHelperImpl(this)).because(exception).error(message);
		}
	}

	@Override
	public void error(String message, Object...data) {
		if (isError()) {
			GenericLogMachineGenerator.start(new GenericHelperImpl(this)).error(message, data);
		}
	}

	@Override
	public void warn(String message, Throwable exception) {
		if (isWarn()) {
			GenericLogMachineGenerator.start(new GenericHelperImpl(this)).because(exception).warn(message);
		}
	}

	@Override
	public void warn(String message, Object...data) {
		if (isWarn()) {
			GenericLogMachineGenerator.start(new GenericHelperImpl(this)).warn(message, data);
		}
	}

	@Override
	public void info(String message, Throwable exception) {
		if (isInfo()) {
			GenericLogMachineGenerator.start(new GenericHelperImpl(this)).because(exception).info(message);
		}
	}

	@Override
	public void info(String message, Object...data) {
		if (isInfo()) {
			GenericLogMachineGenerator.start(new GenericHelperImpl(this)).info(message, data);
		}
	}

	@Override
	public void debug(String message, Throwable exception) {
		if (isDebug()) {
			GenericLogMachineGenerator.start(new GenericHelperImpl(this)).because(exception).debug(message);
		}
	}

	@Override
	public void debug(String message, Object...data) {
		if (isDebug()) {
			GenericLogMachineGenerator.start(new GenericHelperImpl(this)).debug(message, data);
		}
	}

	@Override
	public void trace(String message, Throwable exception) {
		if (isTrace()) {
			GenericLogMachineGenerator.start(new GenericHelperImpl(this)).because(exception).trace(message);
		}
	}

	@Override
	public void trace(String message, Object...data) {
		if (isTrace()) {
			GenericLogMachineGenerator.start(new GenericHelperImpl(this)).trace(message, data);
		}
	}


	// specific builders

	@Override
	public SpecificLogMachineBuilder.$<Void> error() {
		if (isError()) {
			SpecificLogMachineHelper helper = new SpecificHelperImpl(this, Level.ERROR);
			return SpecificLogMachineGenerator.start(helper);
		} else {
			return DEAD_PROXY;
		}
	}

	@Override
	public SpecificLogMachineBuilder.$<Void> warn() {
		if (isWarn()) {
			SpecificLogMachineHelper helper = new SpecificHelperImpl(this, Level.WARN);
			return SpecificLogMachineGenerator.start(helper);
		} else {
			return DEAD_PROXY;
		}
	}

	@Override
	public SpecificLogMachineBuilder.$<Void> info() {
		if (isInfo()) {
			SpecificLogMachineHelper helper = new SpecificHelperImpl(this, Level.INFO);
			return SpecificLogMachineGenerator.start(helper);
		} else {
			return DEAD_PROXY;
		}
	}

	@Override
	public SpecificLogMachineBuilder.$<Void> debug() {
		if (isDebug()) {
			SpecificLogMachineHelper helper = new SpecificHelperImpl(this, Level.DEBUG);
			return SpecificLogMachineGenerator.start(helper);
		} else {
			return DEAD_PROXY;
		}
	}

	@Override
	public SpecificLogMachineBuilder.$<Void> trace() {
		if (isTrace()) {
			SpecificLogMachineHelper helper = new SpecificHelperImpl(this, Level.TRACE);
			return SpecificLogMachineGenerator.start(helper);
		} else {
			return DEAD_PROXY;
		}
	}

	@SuppressWarnings("unchecked")
	private static final SpecificLogMachineBuilder.$<Void> DEAD_PROXY
		= (SpecificLogMachineBuilder.$<Void>) Proxy.newProxyInstance(
			SpecificLogMachineBuilder.$.class.getClassLoader(),
			new Class<?>[]{SpecificLogMachineBuilder.$.class},
			new ProxyHelper()
		);

	@SuppressWarnings("unchecked")
	private final LogMachineBuilders<T> DEAD_SELF_PROXY
		= (LogMachineBuilders<T>) Proxy.newProxyInstance(
			LogMachineBuilders.class.getClassLoader(),
			new Class<?>[]{LogMachineBuilders.class},
			new ProxyHelper()
		);


	// generic builders

	@Override
	public GenericLogMachineBuilder_debug_error_from_from$A_info_to_trace_warn<Void> because(Throwable cause) {
		return GenericLogMachineGenerator.start(new GenericHelperImpl(this)).because(cause);
	}

	@Override
	public GenericLogMachineBuilder_because_debug_error_info_to_trace_warn<Void> from(String location) {
		return GenericLogMachineGenerator.start(new GenericHelperImpl(this)).from(location);
	}

	@Override
	public GenericLogMachineBuilder_because_debug_error_info_to_trace_warn<Void> from() {
		return GenericLogMachineGenerator.start(new GenericHelperImpl(this)).from();
	}

	@Override
	public GenericLogMachineBuilder_because_debug_error_from_from$A_info_trace_warn<Void> to(Topic...topics) {
		return GenericLogMachineGenerator.start(new GenericHelperImpl(this)).to(topics);
	}

	@Override
	public GenericLogMachineBuilder_because_from_from$A_to<Void> with(String key, String value) {
		return GenericLogMachineGenerator.start(new GenericHelperImpl(this)).with(key, value);
	}

	@Override
	public GenericLogMachineBuilder_because_from_from$A_to<Void> with(String key, Number value) {
		return GenericLogMachineGenerator.start(new GenericHelperImpl(this)).with(key, value);
	}

	// conditional builders

	@Override
	public LogMachineBuilders<T> when(Boolean flag) {
		if (flag != null && flag) {
			return this;
		} else {
			return DEAD_SELF_PROXY;
		}
	}
}