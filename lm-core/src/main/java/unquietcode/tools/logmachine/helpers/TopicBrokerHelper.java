package unquietcode.tools.logmachine.helpers;

import unquietcode.tools.logmachine.core.topics.Topic;

import java.util.*;

/**
 * @author Ben Fagin
 * @version 02-17-2013
 */
public class TopicBrokerHelper<_Appender> {
	private final Map<Topic, Set<_Appender>> subscriptions = new WeakHashMap<Topic, Set<_Appender>>();

	public void subscribe(_Appender appender, Topic...topics) {
		for (Topic topic : topics) {
			Set<_Appender> appenders = subscriptions.get(topic);

			if (appenders == null) {
				appenders = new HashSet<_Appender>();
				subscriptions.put(topic, appenders);
			}

			appenders.add(appender);
		}
	}

	public Set<_Appender> getAppenders(Collection<Topic> eventTopics) {
		Set<_Appender> appenders = new HashSet<_Appender>();

		if (eventTopics != null) {
			for (Topic topic : eventTopics) {
				Set<_Appender> candidates = subscriptions.get(topic);
				if (candidates != null) { appenders.addAll(candidates); }
			}
		}

		return appenders;
	}
}
