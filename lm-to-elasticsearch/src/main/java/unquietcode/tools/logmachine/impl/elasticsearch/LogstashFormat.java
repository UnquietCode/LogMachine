package unquietcode.tools.logmachine.impl.elasticsearch;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import unquietcode.tools.logmachine.core.LogEvent;

import java.util.Map;

/**
 * @author Ben Fagin
 * @version 10-24-2012
 *
 * TODO
 * Jackson to smile, possibly use mixins, full name/ short name for enum groups
 * default source?
 */

public class LogstashFormat {


	// use Jackson Smile format

	 //ObjectMapper mapper = new ObjectMapper(new SmileFactory());
	private static final ObjectMapper jackson = new ObjectMapper(); static {
		jackson.setVisibilityChecker(jackson.getVisibilityChecker().with(JsonAutoDetect.Visibility.NONE));
	}


	public byte[] format(LogEvent event) {
		// TODO how to do jackson mixins? then the custom event object can be obliterated

		LogstashEvent _event = new LogstashEvent();
		_event.setMessage(event.getFormattedMessage());
		_event.setTimestamp(event.getTimestamp());
		_event.setSource(event.getLocation());
		_event.setType("lm");
		_event.setLevel(event.getLevel());
		_event.setCause(event.getCause());

		Map<String,String> data = event.getData();

		if (data != null) {
			for (Map.Entry<String, String> field : data.entrySet()) {
				_event.addField(field.getKey(), field.getValue());
			}
		}

		if (event.getGroups() != null) {
			for (Enum anEnum : event.getGroups()) {
				_event.addTag(anEnum.name());       // add a setting to do short name or full name of enums
			}                                       // though maybe do it on the log event? some higher level?
		}

		try {
			return jackson.writerWithType(LogstashEvent.class).writeValueAsBytes(_event);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
