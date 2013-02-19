package unquietcode.tools.logmachine.impl.jdk14;

import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.Switchboard;
import unquietcode.tools.logmachine.helpers.TopicBrokerHelper;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Implementation of {@link Handler} which acts as a
 * broker for subscribers to LogMachine enum topics.
 *
 * @author Ben Fagin
 * @version 02-18-2013
 */
public class JDKTopicBroker extends Handler {
	public static final JDKTopicBroker INSTANCE = new JDKTopicBroker();
	private static final TopicBrokerHelper<Handler> helper = new TopicBrokerHelper<Handler>();

	// subscribe to the root logger on startup
	static {
		Logger.getLogger("").addHandler(INSTANCE);
	}

	/*
		Use the singleton INSTANCE field instead.
	 */
	private JDKTopicBroker() { }


	public static void subscribe(Handler appender, Enum...topics) {
		helper.subscribe(appender, topics);
	}

	private void log(LogRecord event) {
		String lookupKey = "_"+event.getSequenceNumber();
		LogEvent _event = Switchboard.get(lookupKey);

		if (_event == null) {
			return;
		}

		for (Handler handler : helper.getAppenders(_event.getGroups())) {
			handler.publish(event);
		}
	}

	@Override
	public void publish(LogRecord record) {
		log(record);
	}

	@Override
	public void flush() {
		// nothing
	}

	@Override
	public void close() throws SecurityException {
		// nothing
	}
}
