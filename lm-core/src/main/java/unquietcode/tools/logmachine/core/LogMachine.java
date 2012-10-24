package unquietcode.tools.logmachine.core;


import unquietcode.tools.logmachine.LogMachineHelperImpl;
import unquietcode.tools.logmachine.builder.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Benjamin Fagin
 * @version 10-21-2012
 */
public abstract class LogMachine<T> {
	private final LogHandler<T> handler;
	private final T logger;

	protected LogMachine(T logger, LogHandler<T> handler) {
		this.handler = checkNotNull(handler, "Handler cannot be null.");
		this.logger = checkNotNull(logger, "Logger cannot be null.");
	}

	public void _log(LogEvent event) {
		if (event.getGroups() == null) {
			event.setGroups(new ArrayList<Enum>());
		}

		if (event.getReplacements() == null) {
			event.setReplacements(new Object[]{});
		}

		handler.logEvent(logger, event);
	}

//	void _mark(String event, Enum[] categories) {
//		throw new UnsupportedOperationException("not implemented");
//	}
//
//	void _mark(String event) {
//		throw new UnsupportedOperationException("not implemented");
//	}


	//==o==o==o==o==o==o==| log level methods |==o==o==o==o==o==o==//

	public boolean isError() {
		return handler.isError(logger);
	}
	public boolean isErrorEnabled() {
		return handler.isError(logger);
	}

	public boolean isWarn() {
		return handler.isWarn(logger);
	}

	public boolean isWarnEnabled() {
		return handler.isWarn(logger);
	}

	public boolean isInfo() {
		return handler.isInfo(logger);
	}

	public boolean isInfoEnabled() {
		return handler.isInfo(logger);
	}

	public boolean isDebug() {
		return handler.isDebug(logger);
	}

	public boolean isDebugEnabled() {
		return handler.isDebug(logger);
	}

	public boolean isTrace() {
		return handler.isTrace(logger);
	}

	public boolean isTraceEnabled() {
		return handler.isTrace(logger);
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

	// ----------------------------------------------------------------- //

	/**
	 * Creates proxy classes as needed, each which simply returns an empty proxy matching the return
	 * type. Assumes that all return types in the chain are interfaces (a requirement of JDK proxies).
	 * Caches created proxies for each return type.
	 */
	private static class ProxyHelper implements InvocationHandler {
		// TODO Are there any classloader implications from not using a weak map?
		private static final Map<Class, Object> proxyMap = new HashMap<Class, Object>();


		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			Class<?> returnType = method.getReturnType();

			if (proxyMap.containsKey(returnType)) {
				return proxyMap.get(returnType);
			}

			Object returnValue;

			if (returnType.equals(void.class) || returnType.equals(Void.class)) {
				returnValue = null;
			} else {
				returnValue = Proxy.newProxyInstance(returnType.getClassLoader(), new Class<?>[]{returnType}, this);
			}

			proxyMap.put(returnType, returnValue);
			return returnValue;
		}
	}

}