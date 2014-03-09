package unquietcode.tools.logmachine.core;

import unquietcode.tools.logmachine.LazyString;
import unquietcode.tools.logmachine.core.topics.Topic;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Benjamin Fagin
 * @version 02-12-2012
 */
public class LogEvent {
	public static final String TOPICS_KEY = "LM-TOPICS";

	private Level level;
	private String message = "";
	private String location;
	private Throwable cause;
	private List<Topic> topics;
	private Object[] replacements;
	private Map<String, Object> data;
	private long timestamp = System.currentTimeMillis();
	private String threadName = Thread.currentThread().getName();
	private String loggerName;


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

	public List<Topic> getTopics() {
		return topics != null ? topics : (topics = new ArrayList<Topic>());
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public Object[] getReplacements() {
		return replacements != null ? replacements : (replacements = new Object[0]);
	}

	public void setReplacements(Object[] replacements) {

		// modify the replacements as needed
		if (cause == null && replacements.length > 0) {
			Object last = replacements[replacements.length-1];

			// if the last arg is an exception, we'll consider it
			// to be an implicit kind of thing
			if (last instanceof Throwable) {
				setCause((Throwable) last);
				this.replacements = Arrays.copyOf(replacements, replacements.length-1);
				return;
			}
		}

		this.replacements = replacements;
	}

	public Map<String, Object> getData() {
		return data != null ? data : (data = new HashMap<String, Object>());
	}

	//==o==o==o==o==o==o==| Topics |==o==o==o==o==o==o==//

	public String getTopicString() {
		return topicString.getString();
	}

	private final LazyString topicString = new LazyString() {
		protected String _getString() {
			List<Topic> topics = getTopics();
			if (topics == null || topics.isEmpty()) { return ""; }

			StringBuilder sb = new StringBuilder().append("[");
			boolean first = true;

			for (Topic topic : topics) {
				if (first) {
					first = false;
				} else {
					sb.append(" | ");
				}

				sb.append(topic);
			}

			return sb.append("] ").toString();
		}
	};

	//==o==o==o==o==o==o==| Message Formatting |==o==o==o==o==o==o==//

	public String getFormattedMessage() {
		return formattedMessage.getString();
	}

	private static final Pattern REPLACEMENT_PATTERN = Pattern.compile("\\{\\s*(.*?)\\s*\\}");

	private final LazyString formattedMessage = new LazyString() {
		protected String _getString() {
			String string = createString(message, true);
			string = createString(string, false);
			return string;
		}

		private String createString(final String inputString, final boolean assignmentOnly) {
			Matcher matcher = REPLACEMENT_PATTERN.matcher(inputString);
			StringBuilder builder = new StringBuilder();
			int i = 0;
			AtomicInteger idx = new AtomicInteger(0);

			while (matcher.find()) {
				builder.append(inputString.substring(i, matcher.start()));

				String match = matcher.group(1);
				if (match.isEmpty()) {
					if (idx.get() >= replacements.length) {
						builder.append("{}");
					} else {
						builder.append(replacements[idx.getAndIncrement()]);
					}
				} else {
					String string = processReplacement(match, idx, assignmentOnly);

					// if it's null, then give up and stitch it back together
					if (string != null) {
						builder.append(string);
					} else {
						builder.append("{").append(match).append("}");
					}
				}

				i = matcher.end();
			}

			builder.append(inputString.substring(i, inputString.length()));
			return builder.toString();
		}
	};

	private String processReplacement(String match, AtomicInteger idx, boolean assignmentOnly) {

		// argument number
		if (!assignmentOnly) try {
			int index = Integer.parseInt(match.trim()) - 1;

			if (index >= 0  &&  index < replacements.length) {
				return String.valueOf(replacements[index]);
			}
		} catch (NumberFormatException ex) {
			// keep going
		}

		// by here we expect at least one special character
		int length = match.length();

		if (length == 1) {
			return null;
		}

		final char firstChar = match.charAt(0);

		// if we're in assignment mode, bail on non-assignments
		if (assignmentOnly  &&  firstChar != '@') {
			return null;
		}

		final String key = match.substring(1, length).trim();

		switch (firstChar) {

			// group number
			case '~': {
				try {
					int index = Integer.parseInt(key) - 1;

					if (index >= 0  &&  index < topics.size()) {
						return topics.get(index).toString();
					} else {
						return null;
					}
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
