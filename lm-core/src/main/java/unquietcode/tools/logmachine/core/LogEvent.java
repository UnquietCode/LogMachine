package unquietcode.tools.logmachine.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	private String formattedMessage;







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

	private static final Pattern REPLACEMENT_PATTERN = Pattern.compile("\\{\\s*(.*?)\\s*\\}");

	public String getFormattedMessage() {
		if (formattedMessage != null) {
			return formattedMessage;
		}

		Matcher matcher = REPLACEMENT_PATTERN.matcher(message);
		StringBuilder builder = new StringBuilder();
		int i = 0;
		int idx = 0;

		while (matcher.find()) {
			builder.append(message.substring(i, matcher.start()));

			String match = matcher.group(1);
			if (match.isEmpty()) {
				builder.append(replacements[idx++]);
			} else {
				String s = processReplacement(match);

				// if it's null, then stitch it back together
				if (s != null) {
					builder.append(processReplacement(match));
				} else {
					builder.append("{").append(match).append("}");
				}
			}

			i = matcher.end();
		}

		builder.append(message.substring(i, message.length()));
		return formattedMessage = builder.toString();
	}

	private String processReplacement(String match) {
		int length = match.length();

		if (length == 1) {
			return null;
		}

		char firstChar = match.charAt(0);

		switch (firstChar) {
			case '~': {
				try {
					int index = Integer.parseInt(match.substring(1, length));
					if (index != 0) {
						--index;
					}
					return index >= groups.size() ? null : groups.get(index).toString();
				} catch (NumberFormatException ex) {
					return null;
				}
			}

			case ':': {
				if (data == null) {
					return null;
				}

				match = match.substring(1, length);

				if (!data.containsKey(match)) {
					return null;
				}

				String value = data.get(match);
				return value != null ? value : "null";
			}

			default: {
				return null;
			}
		}
	}
}
