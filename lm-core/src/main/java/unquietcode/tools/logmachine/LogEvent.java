package unquietcode.tools.logmachine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Benjamin Fagin
 * @version 02-12-2012
 */
public class LogEvent {
	private Level level;
	private String message = "";
	private String location;
	private Throwable cause;
	private List<Enum> groups;
	private List<Object> replacements;
	private Map<String, String> data;


	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	public List<Enum> getGroups() {
		return groups;
	}

	public void setGroups(List<Enum> groups) {
		this.groups = groups;
	}

	public List<Object> getReplacements() {
		return replacements;
	}

	public void setReplacements(List<Object> replacements) {
		this.replacements = replacements;
	}

	public Map<String, String> getData() {
		if (data == null) {
			data = new HashMap<String, String>();
		}
		return data;
	}

	public String getFormattedMessage() {
		// TODO formatted message output

		return message;
	}
}
