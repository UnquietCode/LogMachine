package unquietcode.tools.logmachine.core;


import unquietcode.tools.logmachine.GenericHelperImpl;
import unquietcode.tools.logmachine.SpecificHelperImpl;
import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineBuilder;
import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineGenerator;
import unquietcode.tools.logmachine.builder.specific.SpecificLogMachine.SpecificLogMachineBuilder;
import unquietcode.tools.logmachine.builder.specific.SpecificLogMachine.SpecificLogMachineGenerator;
import unquietcode.tools.logmachine.core.topics.Topic;
import unquietcode.tools.logmachine.core.topics.TopicBroker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Not really used as a base in terms of type hierarchy, but merely as
 * a cleaner way of dividing up the logic into two smaller files.
 *
 * @author Benjamin Fagin
 * @version 10-21-2012
 * @see LogMachine
 */
public abstract class BaseLogMachine<T> implements LoggingComponent, LogMachineBuilders_when<T> {
	private final List<DataProvider> dataProviders = new CopyOnWriteArrayList<DataProvider>();
	private final List<LoggingComponent> components = new CopyOnWriteArrayList<LoggingComponent>();
	private final LogHandler<T> handler;
	private final T logger;

	protected BaseLogMachine(T logger, LogHandler<T> handler) {
		this.handler = checkNotNull(handler, "Handler cannot be null.");
		this.logger = checkNotNull(logger, "Logger cannot be null.");
	}

	@Override
	public void handle(LogEvent event) {
		_log(event);
	}

	public void _log(LogEvent event) {
		// mix in extra data from the DataProviders list
		for (DataProvider dataProvider : dataProviders) {
			Map<String, Object> extraData = new HashMap<String, Object>();
			dataProvider.addData(extraData);

			Map<String, Object> data = event.getData();

			// ignore any data already set in the event
			for (Map.Entry<String, Object> entry : extraData.entrySet()) {
				String key = entry.getKey();

				if (!data.containsKey(key)) {
					data.put(key, entry.getValue());
				}
			}
		}

		// use the handler, Luke
		handler.logEvent(logger, event);

		// run through our components list
		handle(components, event);

		// notify subscribers to the event topics
		Set<LoggingComponent> topicComponents = TopicBroker.getComponents(event.getGroups());
		handle(topicComponents, event);
	}

	private void handle(Collection<LoggingComponent> components, LogEvent event) {
		for (LoggingComponent component : components) {
			if (component == this) { continue; }
			component.handle(event);
		}
	}

	public void subscribe(Topic...topics) {
		TopicBroker.subscribe(this, topics);
	}

	// ----------- data providers -----------

	public void addDataProvider(DataProvider provider) {
		dataProviders.add(checkNotNull(provider));
	}

	public void addComponent(LoggingComponent component) {
		components.add(checkNotNull(component));
	}

	protected GenericLogMachineBuilder.Start genericBuilder() {
		GenericHelperImpl helper = new GenericHelperImpl(this);
		return GenericLogMachineGenerator.start(helper, helper.methodListener);
	}

	protected SpecificLogMachineBuilder.Start specificBuilder(Level level) {
		SpecificHelperImpl helper = new SpecificHelperImpl(this, level);
		return SpecificLogMachineGenerator.start(helper, helper.methodListener);
	}

	@Override
	public T getNativeLogger() {
		return logger;
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

	protected static String makeLoggerName(Topic[] topics) {
		StringBuilder sb = new StringBuilder();

		for (Topic topic : topics) {
			sb.append(topic.name()).append("_");
		} sb.deleteCharAt(sb.length()-1);

		return sb.toString();
	}

	protected static <T> T[] combine(T first, T[] rest) {
		T[] retval = Arrays.copyOf(rest, rest.length+1);
		retval[retval.length-1] = first;
		return retval;
	}
}