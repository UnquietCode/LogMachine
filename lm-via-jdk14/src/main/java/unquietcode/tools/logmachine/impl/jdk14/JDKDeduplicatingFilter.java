package unquietcode.tools.logmachine.impl.jdk14;

import unquietcode.tools.logmachine.helpers.DeduplicatingFilterHelper;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

/**
 * @author Ben Fagin
 * @version 02-17-2013
 */
public class JDKDeduplicatingFilter implements Filter {
	private final DeduplicatingFilterHelper helper = new DeduplicatingFilterHelper();

	@Override
	public boolean isLoggable(LogRecord record) {
		return !helper.isDuplicate(record);
	}
}
