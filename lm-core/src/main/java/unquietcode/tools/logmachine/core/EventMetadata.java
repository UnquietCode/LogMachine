package unquietcode.tools.logmachine.core;

import java.util.List;
import java.util.Map;

/**
 * Container for any additional information not captured by the logging framework.
 * We use this class to transfer additional information with a logging event.
 *
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class EventMetadata {
	private List<Enum> groups;
	private String location;
	private Map<String, String> data;


	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public List<Enum> getGroups() {
		return groups;
	}

	public void setGroups(List<Enum> groups) {
		this.groups = groups;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
