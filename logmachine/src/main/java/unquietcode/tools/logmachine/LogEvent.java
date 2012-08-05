package unquietcode.tools.logmachine;

import java.util.List;

/**
 * @author Benjamin Fagin
 * @version 02-12-2012
 */
public class LogEvent {
	public final String logger = "";
	public final Level level;
	public final String message;
	public final String source;
	public final Throwable cause;
	public final List<Enum> groups;
	public final Object[] data;

	public LogEvent(Level level, String message, Object[] data, String source, Throwable cause, List<Enum> groups) {
		this.level = level;
		this.message = message;
		this.source = source;
		this.groups = groups;
		this.cause = cause;
		this.data = data;
	}	
}
