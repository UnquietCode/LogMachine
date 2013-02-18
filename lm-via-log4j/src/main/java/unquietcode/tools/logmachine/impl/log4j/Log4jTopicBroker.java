package unquietcode.tools.logmachine.impl.log4j;

import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.helpers.TopicBrokerHelper;

import java.util.Collection;

/**
 * Implementation of {@link org.apache.log4j.Appender} which acts as a
 * broker for subscribers to LogMachine enum topics.
 *
 * @author Ben Fagin
 * @version 02-18-2013
 */
public class Log4jTopicBroker extends AppenderSkeleton {
	public static final Log4jTopicBroker INSTANCE = new Log4jTopicBroker();
	private static final TopicBrokerHelper<Appender> helper = new TopicBrokerHelper<Appender>();

	// subscribe to the root logger on startup
	static {
		Logger.getRootLogger().addAppender(INSTANCE);
	}

	/*
		Use the singleton INSTANCE field instead.
	 */
	private Log4jTopicBroker() { }


	public static void subscribe(Appender appender, Enum...topics) {
		helper.subscribe(appender, topics);
	}

	private void log(LoggingEvent event) {
		@SuppressWarnings("unchecked")
		Collection<Enum> eventTopics = (Collection<Enum>) event.getMDC(LogEvent.TOPICS_KEY);

		for (Appender appender : helper.getAppenders(eventTopics)) {
			appender.doAppend(event);
		}
	}

	@Override
	protected void append(LoggingEvent event) {
		log(event);
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

	@Override
	public void close() {
		// nothing
	}
}
