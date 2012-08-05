package unquietcode.tools.logmachine;

import java.util.List;

/**
 * Container for any additional information not captured by the logging framework.
 * We use this class to transfer additional information with a logging event.
 *
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class EventMetadata {
	private List<Enum> groups;
	private String source;

	public List<Enum> getGroups() {
		return groups;
	}

	public void setGroups(List<Enum> groups) {
		this.groups = groups;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
