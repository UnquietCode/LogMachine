package unquietcode.tools.logmachine.core;


import unquietcode.tools.logmachine.LogMachineHelperImpl;
import unquietcode.tools.logmachine.builder.Generic.GenericBuilder_because_debug_error_from_from$A_info_to_trace_warn_with_with$A;
import unquietcode.tools.logmachine.builder.Generic.GenericBuilder_because_debug_error_from_from$A_info_trace_warn_with_with$A;
import unquietcode.tools.logmachine.builder.Generic.GenericBuilder_because_debug_error_info_to_trace_warn_with_with$A;
import unquietcode.tools.logmachine.builder.Generic.GenericBuilder_debug_error_from_from$A_info_to_trace_warn_with_with$A;
import unquietcode.tools.logmachine.builder.LogMachine.LogMachineGenerator;
import unquietcode.tools.logmachine.builder.Specific.SpecificBuilder_because_from_from$A_send_to_with_with$A;

import java.lang.reflect.Proxy;

/**
 * @author Benjamin Fagin
 * @version 10-21-2012
 */
public abstract class LogMachine<T> extends BaseLogMachine<T> implements LogMachineBuilders_when {

	protected LogMachine(T logger, LogHandler<T> handler) {
		super(logger, handler);
	}

	//==o==o==o==o==o==o==| logging methods |==o==o==o==o==o==o==//

	// specific one-shots (SLF4J style)

	@Override
	public void error(String message, Throwable exception) {
		if (isError()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().because(exception).error(message);
		}
	}

	@Override
	public void error(String message, Object... data) {
		if (isError()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().error(message, data);
		}
	}

	@Override
	public void warn(String message, Throwable exception) {
		if (isWarn()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().because(exception).warn(message);
		}
	}

	@Override
	public void warn(String message, Object... data) {
		if (isWarn()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().warn(message, data);
		}
	}

	@Override
	public void info(String message, Throwable exception) {
		if (isInfo()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().because(exception).info(message);
		}
	}

	@Override
	public void info(String message, Object... data) {
		if (isInfo()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().info(message, data);
		}
	}

	@Override
	public void debug(String message, Throwable exception) {
		if (isDebug()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().because(exception).debug(message);
		}
	}

	@Override
	public void debug(String message, Object... data) {
		if (isDebug()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().debug(message, data);
		}
	}

	@Override
	public void trace(String message, Throwable exception) {
		if (isTrace()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().because(exception).trace(message);
		}
	}

	@Override
	public void trace(String message, Object... data) {
		if (isTrace()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().trace(message, data);
		}
	}


	// specific builders

	@Override
	public SpecificBuilder_because_from_from$A_send_to_with_with$A<Void> error() {
		if (isError()) {
			LogMachineHelperImpl helper = new LogMachineHelperImpl(this, Level.ERROR);
			return LogMachineGenerator.start(helper).specific();
		} else {
			return DEAD_PROXY;
		}
	}

	@Override
	public SpecificBuilder_because_from_from$A_send_to_with_with$A<Void> warn() {
		if (isWarn()) {
			LogMachineHelperImpl helper = new LogMachineHelperImpl(this, Level.WARN);
			return LogMachineGenerator.start(helper).specific();
		} else {
			return DEAD_PROXY;
		}
	}

	@Override
	public SpecificBuilder_because_from_from$A_send_to_with_with$A<Void> info() {
		if (isInfo()) {
			LogMachineHelperImpl helper = new LogMachineHelperImpl(this, Level.INFO);
			return LogMachineGenerator.start(helper).specific();
		} else {
			return DEAD_PROXY;
		}
	}

	@Override
	public SpecificBuilder_because_from_from$A_send_to_with_with$A<Void> debug() {
		if (isError()) {
			LogMachineHelperImpl helper = new LogMachineHelperImpl(this, Level.DEBUG);
			return LogMachineGenerator.start(helper).specific();
		} else {
			return DEAD_PROXY;
		}
	}

	@Override
	public SpecificBuilder_because_from_from$A_send_to_with_with$A<Void> trace() {
		if (isError()) {
			LogMachineHelperImpl helper = new LogMachineHelperImpl(this, Level.TRACE);
			return LogMachineGenerator.start(helper).specific();
		} else {
			return DEAD_PROXY;
		}
	}

	@SuppressWarnings("unchecked")
	private static final SpecificBuilder_because_from_from$A_send_to_with_with$A<Void> DEAD_PROXY
		= (SpecificBuilder_because_from_from$A_send_to_with_with$A<Void>) Proxy.newProxyInstance(
			SpecificBuilder_because_from_from$A_send_to_with_with$A.class.getClassLoader(),
			new Class<?>[]{SpecificBuilder_because_from_from$A_send_to_with_with$A.class},
			new ProxyHelper()
		);

	@SuppressWarnings("unchecked")
	private static final LogMachineBuilders DEAD_SELF_PROXY
		= (LogMachineBuilders) Proxy.newProxyInstance(
			LogMachineBuilders.class.getClassLoader(),
			new Class<?>[]{LogMachineBuilders.class},
			new ProxyHelper()
		);


	// generic builders

	@Override
	public GenericBuilder_debug_error_from_from$A_info_to_trace_warn_with_with$A<Void> because(Throwable cause) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().because(cause);
	}

	@Override
	public GenericBuilder_because_debug_error_info_to_trace_warn_with_with$A<Void> from(String location) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().from(location);
	}

	@Override
	public GenericBuilder_because_debug_error_info_to_trace_warn_with_with$A<Void> from() {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().from();
	}

	@Override
	public GenericBuilder_because_debug_error_from_from$A_info_trace_warn_with_with$A<Void> to(Enum... topics) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().to(topics);
	}

	@Override
	public GenericBuilder_because_debug_error_from_from$A_info_to_trace_warn_with_with$A<Void> with(String key, String value) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().with(key, value);
	}

	@Override
	public GenericBuilder_because_debug_error_from_from$A_info_to_trace_warn_with_with$A<Void> with(String key, Number value) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().with(key, value);
	}


	// conditional builders

	@Override
	public LogMachineBuilders when(boolean flag) {
		if (flag) {
			return this;
		} else {
			return DEAD_SELF_PROXY;
		}
	}
}