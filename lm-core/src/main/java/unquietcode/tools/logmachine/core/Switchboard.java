package unquietcode.tools.logmachine.core;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class Switchboard {
	private static final Map<String, EventMetadata> metadataMap = new WeakHashMap<String, EventMetadata>();
	public static final String MDC_KEY = "__EVENT_METADATA";

	private static long counter = 1;

	public static String put(EventMetadata metadata) {
		String key = Long.toString(counter++);
		metadataMap.put(key, metadata);
		return key;
	}

	public static EventMetadata get(String lookupKey) {
		return metadataMap.get(lookupKey);
	}
}
