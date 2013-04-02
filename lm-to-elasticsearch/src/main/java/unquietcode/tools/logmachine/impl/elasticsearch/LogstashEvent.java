package unquietcode.tools.logmachine.impl.elasticsearch;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import unquietcode.tools.logmachine.core.Level;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Object to be marshalled into logstash JSON object, to be sent to ElasticSearch.
 * https://github.com/logstash/logstash/wiki/logstash's-internal-message-format
 *
 * NOTE: I am pre-emptively using a proposed newer format:
 * https://logstash.jira.com/browse/LOGSTASH-675
 *
 * @author Ben Fagin
 * @version 10-24-2012
 */
public class LogstashEvent {
	private static final ObjectMapper mapper = new ObjectMapper();
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); static {
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	@JsonProperty("@timestamp")
	private String timestamp;

	@JsonProperty("@version")
	public final int version = 1;

	@JsonProperty
	private String message;

	@JsonProperty
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String location;

	@JsonProperty
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<String> topics = new ArrayList<String>();

	@JsonProperty
	private Level level;

	@JsonProperty
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String host;

	@JsonProperty
	@JsonInclude(JsonInclude.Include.NON_EMPTY) // TODO jackson does not honor this
	private final ObjectNode fields = mapper.createObjectNode();

	@JsonProperty
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private ThrowableInfo cause;


	public void setCause(Throwable cause) {
		this.cause = ThrowableInfo.create(cause);
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = dateFormat.format(new Date(timestamp));
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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public List<String> getTopics() {
		return Collections.unmodifiableList(topics);
	}

	public ObjectNode getFields() {
		return fields;
	}

	// ----------------------- //

	public void addTopic(String topic) {
		topics.add(topic);
	}

	public void addField(String key, long value) {
		fields.put(key, value);
	}

	public void addField(String key, int value) {
		fields.put(key, value);
	}

	public void addField(String key, double value) {
		fields.put(key, value);
	}

	public void addField(String key, boolean value) {
		fields.put(key, value);
	}

	public void addField(String key, String value) {
		fields.put(key, value);
	}

	// ------------------------ //

	private static class ThrowableInfo {
		@JsonProperty("class")
		String clazz;

		@JsonProperty
		String message;

		@JsonProperty
		final List<String> stacktrace = new ArrayList<String>();

		@JsonProperty
		ThrowableInfo cause;  // show this one even when null


		static ThrowableInfo create(Throwable throwable) {
			if (throwable == null) {
				return null;
			}

			ThrowableInfo info = new ThrowableInfo();
			ThrowableInfo root = info;

			while (throwable != null) {
				info.message = throwable.getMessage();
				info.clazz = throwable.getClass().getName();

				for (StackTraceElement ste : throwable.getStackTrace()) {
					info.stacktrace.add(ste.toString());
				}

				if (info.message == null) { info.message = ""; }

				if (throwable.getCause() != null && throwable.getCause() != throwable) {
					ThrowableInfo next = new ThrowableInfo();
					info.cause = next;
					info = next;
				}

				throwable = throwable.getCause();
			}

			return root;
		}
	}
}
