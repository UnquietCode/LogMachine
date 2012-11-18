package unquietcode.tools.logmachine.core;


import unquietcode.tools.logmachine.LogMachineHelperImpl;
import unquietcode.tools.logmachine.builder.*;

import java.lang.reflect.Proxy;

/**
 * @author Benjamin Fagin
 * @version 10-21-2012
 */
public abstract class LogMachine<T> extends BaseLogMachine<T> {

	protected LogMachine(T logger, LogHandler<T> handler) {
		super(logger, handler);
	}

	//==o==o==o==o==o==o==| logging methods |==o==o==o==o==o==o==//

	// specific one-shots (SLF4J style)

	public void error(String message, Throwable exception) {
		if (isError()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().because(exception).error(message);
		}
	}

	public void error(String message, Object...data) {
		if (isError()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().error(message, data);
		}
	}

	public void warn(String message, Throwable exception) {
		if (isWarn()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().because(exception).warn(message);
		}
	}

	public void warn(String message, Object...data) {
		if (isWarn()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().warn(message, data);
		}
	}

	public void info(String message, Throwable exception) {
		if (isInfo()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().because(exception).info(message);
		}
	}

	public void info(String message, Object...data) {
		if (isInfo()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().info(message, data);
		}
	}

	public void debug(String message, Throwable exception) {
		if (isDebug()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().because(exception).debug(message);
		}
	}

	public void debug(String message, Object...data) {
		if (isDebug()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().debug(message, data);
		}
	}

	public void trace(String message, Throwable exception) {
		if (isTrace()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().because(exception).trace(message);
		}
	}

	public void trace(String message, Object...data) {
		if (isTrace()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().trace(message, data);
		}
	}


	// specific builders

	public SpecificBuilder_because_from_to<Void> error() {
		if (isError()) {
			LogMachineHelperImpl helper = new LogMachineHelperImpl(this, Level.ERROR);
			return LogMachineGenerator.start(helper).specific();
		} else {
			return DEAD_PROXY;
		}
	}

	public SpecificBuilder_because_from_to<Void> warn() {
		if (isWarn()) {
			LogMachineHelperImpl helper = new LogMachineHelperImpl(this, Level.WARN);
			return LogMachineGenerator.start(helper).specific();
		} else {
			return DEAD_PROXY;
		}
	}

	public SpecificBuilder_because_from_to<Void> info() {
		if (isInfo()) {
			LogMachineHelperImpl helper = new LogMachineHelperImpl(this, Level.INFO);
			return LogMachineGenerator.start(helper).specific();
		} else {
			return DEAD_PROXY;
		}
	}

	public SpecificBuilder_because_from_to<Void> debug() {
		if (isError()) {
			LogMachineHelperImpl helper = new LogMachineHelperImpl(this, Level.DEBUG);
			return LogMachineGenerator.start(helper).specific();
		} else {
			return DEAD_PROXY;
		}
	}

	public SpecificBuilder_because_from_to<Void> trace() {
		if (isError()) {
			LogMachineHelperImpl helper = new LogMachineHelperImpl(this, Level.TRACE);
			return LogMachineGenerator.start(helper).specific();
		} else {
			return DEAD_PROXY;
		}
	}

	@SuppressWarnings("unchecked")
	private static final SpecificBuilder_because_from_to<Void> DEAD_PROXY
		= (SpecificBuilder_because_from_to<Void>) Proxy.newProxyInstance(
			SpecificBuilder_because_from_to.class.getClassLoader(),
			new Class<?>[]{SpecificBuilder_because_from_to.class},
			new ProxyHelper()
		);


	// generic builders

	public GenericBuilder_from_to<Void> because(Throwable cause) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().because(cause);
	}

	public GenericBuilder_because_to<Void> from(String location) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().from(location);
	}

	public GenericBuilder_because_from<Void> to(Enum... categories) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().to(categories);
	}

	public GenericBuilder_because_from_to<Void> with(String key, String value) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().with(key, value);
	}

	public GenericBuilder_because_from_to<Void> with(String key, Number value) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().with(key, value);
	}
}