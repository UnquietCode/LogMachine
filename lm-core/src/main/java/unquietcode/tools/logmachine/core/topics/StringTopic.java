package unquietcode.tools.logmachine.core.topics;

/**
 * Creates a topic from an interned String.
 *
 * @author Ben Fagin
 * @version 2013-08-22
 */
public class StringTopic implements HierarchicalTopic {
	private final String topic;
	private final Topic parent;

	public StringTopic(String topic) {
		this(topic, null);
	}

	public StringTopic(String topic, Topic parent) {
		if (topic == null || (topic = topic.trim()).isEmpty()) {
			throw new IllegalArgumentException("A non-empty topic is required.");
		}
		this.topic = topic.intern();
		this.parent = parent;
	}


	@Override
	public Topic getParent() {
		return parent;
	}

	@Override
	public String name() {
		return topic;
	}
}
