package unquietcode.tools.logmachine.impl.elasticsearch;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import unquietcode.tools.logmachine.core.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Object to be marshalled into logstash JSON object, to be sent to ElasticSearch.
 *
 * https://github.com/logstash/logstash/wiki/logstash's-internal-message-format
 *
 * @author Ben Fagin
 * @version 10-24-2012
 */
public class LogstashEvent {
	private static final ObjectMapper mapper = new ObjectMapper();


	@JsonProperty("@timestamp")
	private long timestamp;

	@JsonProperty("@message")
	private String message;

	@JsonProperty("@source")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String source;

	@JsonProperty("@type")
	private String type = "lm";

	@JsonProperty("@tags")
	private final List<String> tags = new ArrayList<String>();

	@JsonProperty("@level")
	private Level level;

	@JsonProperty("@cause")
	private ThrowableInfo cause;
	// TODO cause should be like this: (but maybe wait until mixins)

	/*

			@cause: {
				message: "",
				stacktrace: [
					"wtf",
					"oh no",
					"aaaaahhh!"
				],
				cause: {
				    // etc. as above
				}



			}
	 */

	private static class ThrowableInfo {
		@JsonProperty
		String message;

		@JsonProperty
		List<String> stacktrace = new ArrayList<String>();

		@JsonProperty
		ThrowableInfo cause;

		@JsonProperty("class")
		String clazz;


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


	@JsonProperty("@fields")
	private final ObjectNode fields = mapper.createObjectNode();


	public void setCause(Throwable cause) {
		this.cause = ThrowableInfo.create(cause);
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getTags() {
		return Collections.unmodifiableList(tags);
	}

	public ObjectNode getFields() {
		return fields;
	}

	// ----------------------- //

	public void addTag(String tag) {
		tags.add(tag);
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

	public void addField(String key, String value) {
		fields.put(key, value);
	}
}
