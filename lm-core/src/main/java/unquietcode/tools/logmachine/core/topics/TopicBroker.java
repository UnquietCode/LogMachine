package unquietcode.tools.logmachine.core.topics;

import unquietcode.tools.logmachine.core.Level;
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
	private static Map<Topic, Set<WeakReference<LoggingComponent>>> subscriptions
		= new WeakHashMap<Topic, Set<WeakReference<LoggingComponent>>>();

	private static final Map<Topic, Level> levels = new WeakHashMap<Topic, Level>();

	private TopicBroker() { }


	public static synchronized void subscribe(LoggingComponent appender, Topic...topics) {
		checkNotNull(appender);
		subscriptions = new WeakHashMap<Topic, Set<WeakReference<LoggingComponent>>>(subscriptions);

		for (Topic topic : topics) {
			Set<WeakReference<LoggingComponent>> appenders = subscriptions.get(topic);

			if (appenders == null) {
				appenders = new HashSet<WeakReference<LoggingComponent>>();
				subscriptions.put(topic, appenders);
			}

			appenders.add(new WeakReference<LoggingComponent>(appender));
		}
	}

	public static Set<LoggingComponent> getComponents(Collection<Topic> eventTopics, Level level) {
		List<Topic> topics = new ArrayList<Topic>();

		// filter topics by level
		for (Topic topic : eventTopics) {
			Level topicLevel = levels.get(topic);

			if (topicLevel == null || topicLevel.isFinerOrEqual(level)) {
				topics.add(topic);
			}
		}

		return getComponents(new HashSet<Topic>(), topics);
	}

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
				components.addAll(getComponents(seen, parents));
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

	public static void setLevel(Level level, Topic...topics) {
		for (Topic topic : topics) {
			if (level != null) {
				levels.put(topic, level);
			} else {
				levels.remove(topic);
			}
		}
	}
}