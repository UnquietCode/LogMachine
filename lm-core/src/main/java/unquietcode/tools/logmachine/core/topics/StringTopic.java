package unquietcode.tools.logmachine.core.topics;

import java.util.Arrays;
import java.util.List;

/**
 * Creates a topic from an interned String.
 *
 * @author Ben Fagin
 * @version 2013-08-22
 */
public class StringTopic implements HierarchicalTopic {
	private final String topic;
	private final List<Topic> parents;

	public StringTopic(String topic) {
		this(topic, null);
	}

	public StringTopic(String topic, Topic parent) {
		if (topic == null || (topic = topic.trim()).isEmpty()) {
			throw new IllegalArgumentException("A non-empty topic is required.");
		}
		this.topic = topic.intern();
		this.parents = Arrays.asList(parent);
	}


	@Override
	public List<Topic> getParents() {
		return parents;
	}

	@Override
	public String name() {
		return topic;
	}
}
