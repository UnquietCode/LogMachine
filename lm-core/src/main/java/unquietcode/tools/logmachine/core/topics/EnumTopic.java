package unquietcode.tools.logmachine.core.topics;

import java.util.Arrays;
import java.util.List;

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
	private final List<Topic> parents;

	public EnumTopic(Enum topic) {
		this(topic, null);
	}

	public EnumTopic(Enum topic, Topic parent) {
		if (topic == null) {
			throw new IllegalArgumentException("A non-empty topic is required.");
		}
		this.topic = topic;
		this.parents = Arrays.asList(parent);
	}

	@Override
	public List<Topic> getParents() {
		return parents;
	}

	@Override
	public String name() {
		return topic.name();
	}
}
