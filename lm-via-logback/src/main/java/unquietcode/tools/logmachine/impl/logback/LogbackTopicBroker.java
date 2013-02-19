package unquietcode.tools.logmachine.impl.logback;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import org.slf4j.LoggerFactory;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.Switchboard;
import unquietcode.tools.logmachine.helpers.TopicBrokerHelper;

/**
 * Implementation of {@link Appender} which acts as a
 * broker for subscribers to LogMachine enum topics.
 *
 * @author Ben Fagin
 * @version 02-18-2013
 */
public class LogbackTopicBroker extends UnsynchronizedAppenderBase<ILoggingEvent> {
	public static final LogbackTopicBroker INSTANCE = new LogbackTopicBroker();
	private static final TopicBrokerHelper<Appender> helper = new TopicBrokerHelper<Appender>();

	// subscribe to the root logger on startup
	static {
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		INSTANCE.setContext(root.getLoggerContext());
		root.addAppender(INSTANCE);
		INSTANCE.start();
	}

	/*
		Use the singleton INSTANCE field instead.
	 */
	private LogbackTopicBroker() { }


	public static void subscribe(Appender appender, Enum...topics) {
		helper.subscribe(appender, topics);
	}

	@SuppressWarnings("unchecked")
	private void log(ILoggingEvent event) {
		String lookupKey = event.getMDCPropertyMap().get(Switchboard.MDC_KEY);
		LogEvent _event = Switchboard.get(lookupKey);

		if (_event == null) {
			return;
		}

		for (Appender appender : helper.getAppenders(_event.getGroups())) {
			appender.doAppend(event);
		}
	}

	@Override
	protected void append(ILoggingEvent event) {
		log(event);
	}

	@Override
	public void start() {
		super.start();
	}

	@Override
	public void stop() {
		super.stop();
	}
}
