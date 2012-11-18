package unquietcode.tools.logmachine.impl.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unquietcode.tools.logmachine.core.GroupingLogHandler;
import unquietcode.tools.logmachine.core.GroupingLogMachine;

/**
 * Primarily for testing. No hierarchies.
 *
 * @author Ben Fagin
 * @version 10-23-2012
 */
public class SLF4JGroupingLogMachine extends GroupingLogMachine<Logger> {
	private static final SLF4JHandler HANDLER = new SLF4JHandler();

	public SLF4JGroupingLogMachine(Enum...groups) {
		super(
			LoggerFactory.getLogger(GroupingLogHandler.GROUP_LOGGER),
			new GroupingLogHandler<Logger>(HANDLER, getLoggers(groups)),
			groups
		);
	}

	private static Logger[] getLoggers(Enum...groups) {
		Logger[] retval = new Logger[groups.length];

		for (int i=0; i < groups.length; i++) {
			retval[i] = LoggerFactory.getLogger(groups[i].getClass());
		}

		return retval;
	}
}
