package unquietcode.tools.logmachine;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class Switchboard {
	public static final Map<String, EventMetadata> metadataMap = new HashMap<String, EventMetadata>();
	public static final String MDC_KEY = "__EVENT_METADATA";

	private static long counter = 1;

	public static String put(EventMetadata metadata) {
		String key = Long.toString(counter++);
		metadataMap.put(key, metadata);
		return key;
	}
}
