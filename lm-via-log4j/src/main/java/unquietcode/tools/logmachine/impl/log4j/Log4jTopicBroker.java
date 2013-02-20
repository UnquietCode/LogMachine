package unquietcode.tools.logmachine.impl.log4j;

import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.Switchboard;
import unquietcode.tools.logmachine.helpers.TopicBrokerHelper;

import static com.google.common.base.Preconditions.checkNotNull;

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
		helper.subscribe(checkNotNull(appender), checkNotNull(topics));
	}

	@Override
	protected void append(LoggingEvent event) {
		String lookupKey = (String) event.getMDC(Switchboard.MDC_KEY);
		LogEvent _event = Switchboard.get(lookupKey);

		if (_event == null) {
			return;
		}

		for (Appender appender : helper.getAppenders(_event.getGroups())) {
			// filtering is done in the superclass doAppend method
			appender.doAppend(event);
		}
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
