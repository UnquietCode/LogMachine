package unquietcode.tools.logmachine.core;

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
	private Object[] replacements;
	private Map<String, String> data;
	private long timestamp;
	private String threadName;
	private String loggerName;
	private EventMetadata metadata;








	// ILoggingEvent methods
	private static final String NOT_IMPLEMENTED = "@_@ - You broke it.";

	public Level getLevel() {
		return level;
	}

	public EventMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(EventMetadata metadata) {
		this.metadata = metadata;
	}

	public String getLoggerName() {
		return loggerName;
	}

	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Level level() {
		return level;
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

	public Object[] getReplacements() {
		return replacements;
	}

	public void setReplacements(Object[] replacements) {
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
