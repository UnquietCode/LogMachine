package unquietcode.tools.logmachine.core.topics;

import unquietcode.tools.logmachine.core.LoggingComponent;

import java.lang.ref.WeakReference;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Sends various messages to various components based on
 * their subscriptions to various topics.
 *
 * @author Ben Fagin
 * @version 2014-01-15
 */
public class TopicBroker {
	private static final Map<Topic, Set<WeakReference<LoggingComponent>>> subscriptions
		= new WeakHashMap<Topic, Set<WeakReference<LoggingComponent>>>();

	public static final TopicBroker INSTANCE = new TopicBroker();

	/**
	 * Use INSTANCE field instead.
	 */
	private TopicBroker() { }


	public static synchronized void subscribe(LoggingComponent appender, Topic...topics) {
		checkNotNull(appender);

		for (Topic topic : topics) {
			Set<WeakReference<LoggingComponent>> appenders = subscriptions.get(topic);

			if (appenders == null) {
				appenders = new HashSet<WeakReference<LoggingComponent>>();
				subscriptions.put(topic, appenders);
			}

			appenders.add(new WeakReference<LoggingComponent>(appender));
		}
	}

	public static synchronized Set<LoggingComponent> getComponents(Collection<Topic> eventTopics) {
		return getComponents(new HashSet<Topic>(), eventTopics);
	}

	// @DefaultValues({HashSet.class})
	private static Set<LoggingComponent> getComponents(Set<Topic> seen, Collection<Topic> eventTopics) {
		Set<LoggingComponent> components = new HashSet<LoggingComponent>();
		if (eventTopics == null) { eventTopics = Collections.emptyList(); }

		for (Topic topic : eventTopics) {
			if (seen.contains(topic)) {
				continue;
			} else {
				seen.add(topic);
			}

			if (topic instanceof HierarchicalTopic) {
				List<Topic> parents = ((HierarchicalTopic) topic).getParents();
				components.addAll(getComponents(parents));
			}

			Set<WeakReference<LoggingComponent>> candidates = subscriptions.get(topic);

			if (candidates != null) {

				for (WeakReference<LoggingComponent> candidate : candidates) {
					LoggingComponent component = candidate.get();

					if (component != null) {
						components.add(component);
					}
				}
			}
		}

		return components;
	}
}