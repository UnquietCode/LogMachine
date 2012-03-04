package unquietcode.tools.logmachine;

import java.util.List;

/**
 * @author Benjamin Fagin
 * @version 02-12-2012
 */
public class Entry {
	public final String message;
	public final String source;
	public final Throwable cause;
	public final long timestamp;
	public final List<Enum> groups;

	public Entry(String message, String source, Throwable cause, List<Enum> groups) {
		this.message = message;
		this.source = source;
		this.groups = groups;
		this.cause = cause;
		timestamp = System.currentTimeMillis();
	}	
}
