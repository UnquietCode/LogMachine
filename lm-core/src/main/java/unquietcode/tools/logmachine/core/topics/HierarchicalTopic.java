package unquietcode.tools.logmachine.core.topics;

/**
 * @author Ben Fagin
 * @version 2013-08-22
 */
public interface HierarchicalTopic extends Topic {

	/**
	 * @return the parent of this topic, or null
	 */
	Topic getParent();
}
