package unquietcode.tools.logmachine.core;


import unquietcode.tools.logmachine.GenericHelperImpl;
import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineBuilder;
import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineGenerator;
import unquietcode.tools.logmachine.core.topics.Topic;
import unquietcode.tools.logmachine.core.topics.TopicBroker;

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
public abstract class BaseLogMachine<T> implements LogMachineBuilders<T> {
	private final List<DataProvider> dataProviders = new CopyOnWriteArrayList<DataProvider>();
	private final List<LoggingComponent> components = new CopyOnWriteArrayList<LoggingComponent>();
	private final Set<Topic> defaultTopics = new HashSet<Topic>();
	private final LogHandler<T> handler;
	private final T logger;

	protected BaseLogMachine(T logger, LogHandler<T> handler) {
		this.handler = checkNotNull(handler, "Handler cannot be null.");
		this.logger = checkNotNull(logger, "Logger cannot be null.");
	}

	public void _log(LogEvent event) {
		final Level eventLevel = checkNotNull(event.getLevel());
		final Level loggerLevel = checkNotNull(handler.getLevel(logger));

		// skip if under level
		if (loggerLevel.isCoarserThan(eventLevel)) {
			return;
		}

		// add default topics, if any
		event.getTopics().addAll(defaultTopics);

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
		Set<LoggingComponent> topicComponents = TopicBroker.getComponents(event.getTopics(), eventLevel);
		handle(topicComponents, event);
	}

	private void handle(Collection<LoggingComponent> components, LogEvent event) {
		for (LoggingComponent component : components) {
			if (component == this) { continue; }
			component.handle(event);
		}
	}

	public void addDataProvider(DataProvider provider) {
		dataProviders.add(checkNotNull(provider));
	}

	public void addComponent(LoggingComponent component) {
		components.add(checkNotNull(component));
	}

	public synchronized void setDefaultTopics(Topic...topics) {
		defaultTopics.clear();
		defaultTopics.addAll(Arrays.asList(topics));
	}

	protected GenericLogMachineBuilder.Start genericBuilder() {
		GenericHelperImpl helper = new GenericHelperImpl(this);
		return GenericLogMachineGenerator.start(helper);
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
}