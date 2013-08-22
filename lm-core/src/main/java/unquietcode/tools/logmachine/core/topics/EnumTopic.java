package unquietcode.tools.logmachine.core.topics;

/**
 * Creates a topic from an enum. This can be useful if
 * you are making use of an enum which can't be modified.
 * However, if you have control of the enum class you
 * should consider implementing {@link Topic} directly.
 *
 * @author Ben Fagin
 * @version 2013-08-22
 */
public class EnumTopic implements HierarchicalTopic {
	private final Enum topic;
	private final Topic parent;

	public EnumTopic(Enum topic) {
		this(topic, null);
	}

	public EnumTopic(Enum topic, Topic parent) {
		if (topic == null) {
			throw new IllegalArgumentException("A non-empty topic is required.");
		}
		this.topic = topic;
		this.parent = parent;
	}

	@Override
	public Topic getParent() {
		return parent;
	}

	@Override
	public String name() {
		return topic.name();
	}
}
