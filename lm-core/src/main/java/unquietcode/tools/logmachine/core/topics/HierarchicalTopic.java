package unquietcode.tools.logmachine.core.topics;

import java.util.List;

/**
 * @author Ben Fagin
 * @version 2013-08-22
 */
public interface HierarchicalTopic extends Topic {

	/**
	 * @return the parent of this topic, or null
	 */
	List<Topic> getParents();
}
