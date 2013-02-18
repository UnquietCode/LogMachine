package unquietcode.tools.logmachine.impl.log4j;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;
import unquietcode.tools.logmachine.helpers.DeduplicatingFilterHelper;

/**
 * @author Ben Fagin
 * @version 02-17-2013
 */
public class Log4jDeduplicatingFilter extends Filter {
	private final DeduplicatingFilterHelper helper = new DeduplicatingFilterHelper();

	@Override
	public int decide(LoggingEvent event) {
		boolean isDuplicate = helper.isDuplicate(event);
		return isDuplicate ? Filter.DENY : Filter.NEUTRAL;
	}
}
