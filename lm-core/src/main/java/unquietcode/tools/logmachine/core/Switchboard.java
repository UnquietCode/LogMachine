package unquietcode.tools.logmachine.core;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class Switchboard {
	private static final Map<String, LogEvent> metadataMap = new WeakHashMap<String, LogEvent>();
	public static final String MDC_KEY = "__LOG_MACHINE_EVENT";

	private static long counter = 1;

	public static String put(LogEvent event) {
		String key = Long.toString(counter++);
		metadataMap.put(key, event);
		return key;
	}

	public static LogEvent get(String lookupKey) {
		return metadataMap.get(lookupKey);
	}
}
