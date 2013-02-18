package unquietcode.tools.logmachine.core;


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
public abstract class BaseLogMachine<T> {
	private final LogHandler<T> handler;
	private final T logger;

	protected BaseLogMachine(T logger, LogHandler<T> handler) {
		this.handler = checkNotNull(handler, "Handler cannot be null.");
		this.logger = checkNotNull(logger, "Logger cannot be null.");
	}

	public void _log(LogEvent event) {
		// TODO make these lazy created lists of the object
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

	public String getLoggerName() {
		return handler.getLoggerName(logger);
	}

	// ----------------------------------------------------------------- //

	/**
	 * Creates proxy classes as needed, each which simply returns an empty proxy matching the return
	 * type. Assumes that all return types in the chain are interfaces (a requirement of JDK proxies).
	 * Caches created proxies for each return type.
	 */
	protected static class ProxyHelper implements InvocationHandler {
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