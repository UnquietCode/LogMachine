package unquietcode.tools.logmachine.impl.log4j;

import org.apache.log4j.Logger;
import unquietcode.tools.logmachine.core.GroupingLogHandler;
import unquietcode.tools.logmachine.core.GroupingLogMachine;

/**
 * Primarily for testing. No hierarchies.
 *
 * @author Ben Fagin
 * @version 10-23-2012
 */
public class Log4jGroupingLogMachine extends GroupingLogMachine<Logger> {
	private static final Log4jHandler HANDLER = new Log4jHandler();

	public Log4jGroupingLogMachine(Enum...groups) {
		super(
			Logger.getLogger(GroupingLogHandler.GROUP_LOGGER),
			new GroupingLogHandler<Logger>(HANDLER, getLoggers(groups)),
			groups
		);
	}

	private static Logger[] getLoggers(Enum...groups) {
		Logger[] retval = new Logger[groups.length];

		for (int i=0; i < groups.length; i++) {
			retval[i] = Logger.getLogger(groups[i].getClass());
		}

		return retval;
	}
}
