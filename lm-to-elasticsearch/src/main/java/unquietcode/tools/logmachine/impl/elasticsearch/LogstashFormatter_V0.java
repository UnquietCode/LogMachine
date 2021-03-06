package unquietcode.tools.logmachine.impl.elasticsearch;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.topics.Topic;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author Ben Fagin
 * @version 10-24-2012
 */
public class LogstashFormatter_V0 implements ElasticSearchJSONFormatter {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
	private static final ObjectMapper json = new ObjectMapper(); static {
		json.setVisibilityChecker(json.getVisibilityChecker().with(JsonAutoDetect.Visibility.NONE));
	}

	private static final String HOST_NAME; static {
		String name;

		try {
			name = InetAddress.getLocalHost().getHostName();
		} catch (Exception ex) {
			name = "???";
		}

		HOST_NAME = name;
	}

	private boolean useShortTopicNames = true;


	public byte[] format(LogEvent event) {
		// basic info
		LogstashEvent_V0 _event = new LogstashEvent_V0();
		_event.setMessage(event.getFormattedMessage());
		_event.setTimestamp(event.getTimestamp());
		_event.setLocation(event.getLocation() != null ? event.getLocation() : event.getLoggerName());
		_event.setLevel(event.getLevel());
		_event.setCause(event.getCause());
		_event.setHost(HOST_NAME);

		// fields
		for (Map.Entry<String, Object> field : event.getData().entrySet()) {
			Object value = field.getValue();
			Class type = field.getValue().getClass();

			if (Double.class.isAssignableFrom(type)) {
				_event.addField(field.getKey(), (Double) value);
			} else if (Integer.class.isAssignableFrom(type)) {
				_event.addField(field.getKey(), (Integer) value);
			} else if (Long.class.isAssignableFrom(type)) {
				_event.addField(field.getKey(), (Long) value);
			} else if (Boolean.class.isAssignableFrom(type)) {
				_event.addField(field.getKey(), (Boolean) value);
			} else {
				_event.addField(field.getKey(), value.toString());
			}
		}

		// topics ('tags')
		for (Topic topic : event.getTopics()) {
			if (useShortTopicNames) {
				_event.addTopic(topic.name());
			} else {
				StringBuilder sb = new StringBuilder();
				String[] packageSegments = topic.getClass().getPackage().getName().split("\\.");

				for (String segment : packageSegments) {
					sb.append(segment.charAt(0)).append(".");
				}

				sb.append(topic.getClass().getSimpleName()).append("#").append(topic.name());
				_event.addTopic(sb.toString());
			}
		}

		try {
			return json.writerWithType(LogstashEvent_V1.class).writeValueAsBytes(_event);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void useShortTopicNames(boolean value) {
		useShortTopicNames = value;
	}

	@Override
	public String getIndexName(LogEvent event) {
		return "logstash-"+dateFormat.format(new Date(event.getTimestamp()));
	}
}
