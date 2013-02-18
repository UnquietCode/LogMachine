package unquietcode.tools.logmachine.helpers;

import java.util.*;

/**
 * @author Ben Fagin
 * @version 02-17-2013
 */
public class TopicBrokerHelper<_Appender> {
	private final Map<Enum, Set<_Appender>> subscriptions = new WeakHashMap<Enum, Set<_Appender>>();

	public void subscribe(_Appender appender, Enum...topics) {
		for (Enum topic : topics) {
			Set<_Appender> appenders = subscriptions.get(topic);

			if (appenders == null) {
				appenders = new HashSet<_Appender>();
				subscriptions.put(topic, appenders);
			}

			appenders.add(appender);
		}
	}

	public Set<_Appender> getAppenders(Collection<Enum> eventTopics) {
		Set<_Appender> appenders = new HashSet<_Appender>();

		if (eventTopics != null) {
			for (Enum topic : eventTopics) {
				Set<_Appender> candidates = subscriptions.get(topic);
				if (candidates != null) { appenders.addAll(candidates); }
			}
		}

		return appenders;
	}
}
