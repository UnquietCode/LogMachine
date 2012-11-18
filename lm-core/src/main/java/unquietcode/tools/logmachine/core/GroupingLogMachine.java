package unquietcode.tools.logmachine.core;


import unquietcode.tools.logmachine.LogMachineHelperImpl;
import unquietcode.tools.logmachine.builder.*;

import java.lang.reflect.Proxy;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Benjamin Fagin
 * @version 11-18-2012
 */
public abstract class GroupingLogMachine<T> extends BaseLogMachine<T> {
	private final Enum[] groups;

	protected GroupingLogMachine(T logger, GroupingLogHandler<T> handler, Enum...groups) {
		super(logger, handler);
		this.groups = checkNotNull(groups);
		checkArgument(groups.length > 0, "you must specify at least 1 group");
	}


	//==o==o==o==o==o==o==| logging methods |==o==o==o==o==o==o==//

	// specific one-shots (SLF4J style)

	public void error(String message, Throwable exception) {
		if (isError()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().to(groups).because(exception).error(message);
		}
	}

	public void error(String message, Object...data) {
		if (isError()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().to(groups).error(message, data);
		}
	}

	public void warn(String message, Throwable exception) {
		if (isWarn()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().to(groups).because(exception).warn(message);
		}
	}

	public void warn(String message, Object...data) {
		if (isWarn()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().to(groups).warn(message, data);
		}
	}

	public void info(String message, Throwable exception) {
		if (isInfo()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().to(groups).because(exception).info(message);
		}
	}

	public void info(String message, Object...data) {
		if (isInfo()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().to(groups).info(message, data);
		}
	}

	public void debug(String message, Throwable exception) {
		if (isDebug()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().to(groups).because(exception).debug(message);
		}
	}

	public void debug(String message, Object...data) {
		if (isDebug()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().to(groups).debug(message, data);
		}
	}

	public void trace(String message, Throwable exception) {
		if (isTrace()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().to(groups).because(exception).trace(message);
		}
	}

	public void trace(String message, Object...data) {
		if (isTrace()) {
			LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().to(groups).trace(message, data);
		}
	}


	// specific builders

	public SpecificBuilder_because_from<Void> error() {
		if (isError()) {
			LogMachineHelperImpl helper = new LogMachineHelperImpl(this, Level.ERROR);
			return LogMachineGenerator.start(helper).specific().to(groups);
		} else {
			return DEAD_PROXY;
		}
	}

	public SpecificBuilder_because_from<Void> warn() {
		if (isWarn()) {
			LogMachineHelperImpl helper = new LogMachineHelperImpl(this, Level.WARN);
			return LogMachineGenerator.start(helper).specific().to(groups);
		} else {
			return DEAD_PROXY;
		}
	}

	public SpecificBuilder_because_from<Void> info() {
		if (isInfo()) {
			LogMachineHelperImpl helper = new LogMachineHelperImpl(this, Level.INFO);
			return LogMachineGenerator.start(helper).specific().to(groups);
		} else {
			return DEAD_PROXY;
		}
	}

	public SpecificBuilder_because_from<Void> debug() {
		if (isError()) {
			LogMachineHelperImpl helper = new LogMachineHelperImpl(this, Level.DEBUG);
			return LogMachineGenerator.start(helper).specific().to(groups);
		} else {
			return DEAD_PROXY;
		}
	}

	public SpecificBuilder_because_from<Void> trace() {
		if (isError()) {
			LogMachineHelperImpl helper = new LogMachineHelperImpl(this, Level.TRACE);
			return LogMachineGenerator.start(helper).specific().to(groups);
		} else {
			return DEAD_PROXY;
		}
	}

	@SuppressWarnings("unchecked")
	private static final SpecificBuilder_because_from<Void> DEAD_PROXY
		= (SpecificBuilder_because_from<Void>) Proxy.newProxyInstance(
			SpecificBuilder_because_from.class.getClassLoader(),
			new Class<?>[]{SpecificBuilder_because_from.class},
			new ProxyHelper()
		);


	// generic builders

	public GenericBuilder_from<Void> because(Throwable cause) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().to(groups).because(cause);
	}

	public GenericBuilder_because<Void> from(String location) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().to(groups).from(location);
	}

	public GenericBuilder_because_from<Void> with(String key, String value) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().to(groups).with(key, value);
	}

	public GenericBuilder_because_from<Void> with(String key, Number value) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).generic().to(groups).with(key, value);
	}
}