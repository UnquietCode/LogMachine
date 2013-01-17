package unquietcode.tools.logmachine.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
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
	private Map<String, Object> data;
	private long timestamp = System.currentTimeMillis();
	private String threadName = Thread.currentThread().getName();
	private String loggerName;
	private String formattedMessage;



	// ILoggingEvent methods
	private static final String NOT_IMPLEMENTED = "@_@ - You broke it.";

	public Level getLevel() {
		return level;
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

	public long getTimestamp() {
		return timestamp;
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

	public Map<String, Object> getData() {
		if (data == null) {
			data = new HashMap<String, Object>();
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
		AtomicInteger idx = new AtomicInteger(0);

		while (matcher.find()) {
			builder.append(message.substring(i, matcher.start()));

			String match = matcher.group(1);
			if (match.isEmpty()) {
				if (idx.get() >= replacements.length) {
					builder.append("{}");
				} else {
					builder.append(replacements[idx.getAndIncrement()]);
				}
			} else {
				String string = processReplacement(match, idx);

				// if it's null, then give up and stitch it back together
				if (string != null) {
					builder.append(string);
				} else {
					builder.append("{").append(match).append("}");
				}
			}

			i = matcher.end();
		}

		builder.append(message.substring(i, message.length()));
		return formattedMessage = builder.toString();
	}

	private String processReplacement(String match, AtomicInteger idx) {
		int length = match.length();

		if (length == 1) {
			return null;
		}

		char firstChar = match.charAt(0);
		String key = match.substring(1, length);

		switch (firstChar) {

			// group name
			case '~': {
				try {
					int index = Integer.parseInt(key);
					if (index != 0) {
						--index;
					}
					return index >= groups.size() ? null : groups.get(index).toString();
				} catch (NumberFormatException ex) {
					return null;
				}
			}

			// property name
			case ':': {
				if (data == null) {
					return null;
				}

				if (!data.containsKey(key)) {
					return null;
				}

				Object value = data.get(key);
				return value != null ? value.toString() : "null";
			}

			// property assignment
			case '@': {
				if (data != null) {
					if (data.containsKey(key)) {
						throw new IllegalStateException("Property '"+key+"' cannot be assigned because it already exists.");
					}
				} else {
					data = new HashMap<String, Object>();
				}

				if (idx.get() >= replacements.length) {
					return "{@"+match+"}";
				} else {
					Object value = replacements[idx.getAndIncrement()];
					data.put(key, value);
					return value != null ? value.toString() : "null";
				}
			}

			default: {
				return null;
			}
		}
	}
}
