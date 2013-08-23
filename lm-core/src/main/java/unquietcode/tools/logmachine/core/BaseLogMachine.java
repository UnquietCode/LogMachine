package unquietcode.tools.logmachine.core;


import unquietcode.tools.logmachine.GenericHelperImpl;
import unquietcode.tools.logmachine.SpecificHelperImpl;
import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineBuilder;
import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineGenerator;
import unquietcode.tools.logmachine.builder.specific.SpecificLogMachine.SpecificLogMachineBuilder;
import unquietcode.tools.logmachine.builder.specific.SpecificLogMachine.SpecificLogMachineGenerator;
import unquietcode.tools.logmachine.builder.specific.SpecificLogMachine.SpecificLogMachineHelper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Not really used as a base in terms of type hierarchy, but merely as
 * a cleaner way of dividing up the logic into two smaller files.
 *
 * @author Benjamin Fagin
 * @version 10-21-2012
 * @see LogMachine
 */
public abstract class BaseLogMachine<T> {
	private final List<DataProvider> dataProviders = new ArrayList<DataProvider>();
	private final LogHandler<T> handler;
	private final T logger;


	protected BaseLogMachine(T logger, LogHandler<T> handler) {
		this.handler = checkNotNull(handler, "Handler cannot be null.");
		this.logger = checkNotNull(logger, "Logger cannot be null.");
	}

	public void _log(LogEvent event) {
		// mix in extra data from the DataProviders list
		for (DataProvider dataProvider : dataProviders) {
			Map<String, Object> extraData = new HashMap<String, Object>();
			dataProvider.addData(extraData);
			event.getData().putAll(extraData);
		}

		handler.logEvent(logger, event);
	}

	// ----------- data providers -----------

	public interface DataProvider {
		void addData(Map<String, Object> data);
	}

	// Set the context to be queried and applied to every new event.
	// Changes to the map will be reflected in events as they occur.
	public void addDataProvider(DataProvider provider) {
		dataProviders.add(checkNotNull(provider));
	}

	protected GenericLogMachineBuilder.Start<Void> genericBuilder() {
		return GenericLogMachineGenerator.start(new GenericHelperImpl(this));
	}

	protected SpecificLogMachineBuilder.Start<Void> specificBuilder(Level level) {
		SpecificLogMachineHelper helper = new SpecificHelperImpl(this, level);
		return SpecificLogMachineGenerator.start(helper);
	}

	// --------------------------------------


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

			if (returnType.equals(void.class) || returnType.equals(Void.class) || returnType.equals(Object.class)) {
				returnValue = null;
			} else {
				returnValue = Proxy.newProxyInstance(returnType.getClassLoader(), new Class<?>[]{returnType}, this);
			}

			proxyMap.put(returnType, returnValue);
			return returnValue;
		}
	}
}