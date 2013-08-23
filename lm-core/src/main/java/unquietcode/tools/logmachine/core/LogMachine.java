package unquietcode.tools.logmachine.core;


import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineBuilder_2because_2debug_2error_2from_2from_1A_2info_2trace_2warn;
import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineBuilder_2because_2debug_2error_2info_2to_2trace_2warn;
import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineBuilder_2because_2from_2from_1A_2to;
import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineBuilder_2debug_2error_2from_2from_1A_2info_2to_2trace_2warn;
import unquietcode.tools.logmachine.builder.specific.SpecificLogMachine.SpecificLogMachineBuilder;
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
			genericBuilder().because(exception).error(message);
		}
	}

	@Override
	public void error(String message, Object...data) {
		if (isError()) {
			genericBuilder().error(message, data);
		}
	}

	@Override
	public void warn(String message, Throwable exception) {
		if (isWarn()) {
			genericBuilder().because(exception).warn(message);
		}
	}

	@Override
	public void warn(String message, Object...data) {
		if (isWarn()) {
			genericBuilder().warn(message, data);
		}
	}

	@Override
	public void info(String message, Throwable exception) {
		if (isInfo()) {
			genericBuilder().because(exception).info(message);
		}
	}

	@Override
	public void info(String message, Object...data) {
		if (isInfo()) {
			genericBuilder().info(message, data);
		}
	}

	@Override
	public void debug(String message, Throwable exception) {
		if (isDebug()) {
			genericBuilder().because(exception).debug(message);
		}
	}

	@Override
	public void debug(String message, Object...data) {
		if (isDebug()) {
			genericBuilder().debug(message, data);
		}
	}

	@Override
	public void trace(String message, Throwable exception) {
		if (isTrace()) {
			genericBuilder().because(exception).trace(message);
		}
	}

	@Override
	public void trace(String message, Object...data) {
		if (isTrace()) {
			genericBuilder().trace(message, data);
		}
	}


	// specific builders

	@Override
	public SpecificLogMachineBuilder.Start<Void> error() {
		if (isError()) {
			return specificBuilder(Level.ERROR);
		} else {
			return DEAD_PROXY;
		}
	}

	@Override
	public SpecificLogMachineBuilder.Start<Void> warn() {
		if (isWarn()) {
			return specificBuilder(Level.WARN);
		} else {
			return DEAD_PROXY;
		}
	}

	@Override
	public SpecificLogMachineBuilder.Start<Void> info() {
		if (isInfo()) {
			return specificBuilder(Level.INFO);
		} else {
			return DEAD_PROXY;
		}
	}

	@Override
	public SpecificLogMachineBuilder.Start<Void> debug() {
		if (isDebug()) {
			return specificBuilder(Level.DEBUG);
		} else {
			return DEAD_PROXY;
		}
	}

	@Override
	public SpecificLogMachineBuilder.Start<Void> trace() {
		if (isTrace()) {
			return specificBuilder(Level.TRACE);
		} else {
			return DEAD_PROXY;
		}
	}

	@SuppressWarnings("unchecked")
	private static final SpecificLogMachineBuilder.Start<Void> DEAD_PROXY
		= (SpecificLogMachineBuilder.Start<Void>) Proxy.newProxyInstance(
			SpecificLogMachineBuilder.Start.class.getClassLoader(),
			new Class<?>[]{SpecificLogMachineBuilder.Start.class},
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
	public GenericLogMachineBuilder_2debug_2error_2from_2from_1A_2info_2to_2trace_2warn<Void> because(Throwable cause) {
		return genericBuilder().because(cause);
	}

	@Override
	public GenericLogMachineBuilder_2because_2debug_2error_2info_2to_2trace_2warn<Void> from(String location) {
		return genericBuilder().from(location);
	}

	@Override
	public GenericLogMachineBuilder_2because_2debug_2error_2info_2to_2trace_2warn<Void> from() {
		return genericBuilder().from();
	}

	@Override
	public GenericLogMachineBuilder_2because_2debug_2error_2from_2from_1A_2info_2trace_2warn<Void> to(Topic...topics) {
		return genericBuilder().to(topics);
	}

	@Override
	public GenericLogMachineBuilder_2because_2from_2from_1A_2to<Void> with(String key, String value) {
		return genericBuilder().with(key, value);
	}

	@Override
	public GenericLogMachineBuilder_2because_2from_2from_1A_2to<Void> with(String key, Number value) {
		return genericBuilder().with(key, value);
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