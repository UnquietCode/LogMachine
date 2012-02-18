package unquietcode.tools.logmachine;

import java.util.Iterator;

/**
 * @author Benjamin Fagin
 * @version 02-18-2012
 */
public class LogTraverser implements Iterable<Entry> {
	final LogMachine log;
	
	LogTraverser(LogMachine log) {
		if (log == null) {
			throw new IllegalArgumentException("Log cannot be null.");
		}

		this.log = log;
	}
	
	@Override
	public Iterator<Entry> iterator() {
		return null;
	}
}
