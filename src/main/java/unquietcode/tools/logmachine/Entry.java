package unquietcode.tools.logmachine;

import java.util.List;

/**
 * @author Benjamin Fagin
 * @version 02-12-2012
 */
public class Entry {
	public final String message;
	public final String uri;
	public final long timestamp;
	public final List<Enum> groups;

	public Entry(String message, String uri, List<Enum> groups) {
		this.message = message;
		this.uri = uri;
		this.groups = groups;
		timestamp = System.currentTimeMillis();
	}	
}
