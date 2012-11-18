package unquietcode.tools.logmachine.impl.simple;

import unquietcode.tools.logmachine.core.GroupingLogHandler;
import unquietcode.tools.logmachine.core.GroupingLogMachine;

/**
 * Primarily for testing. No hierarchies.
 *
 * @author Ben Fagin
 * @version 10-23-2012
 */
public class SimpleGroupingLogMachine extends GroupingLogMachine<SimpleLogger> {
	private static final SimpleLogHandler HANDLER = new SimpleLogHandler();

	public SimpleGroupingLogMachine(Enum...groups) {
		super(
			SimpleLogger.getLogger(GroupingLogHandler.GROUP_LOGGER),
			new GroupingLogHandler<SimpleLogger>(HANDLER, getLoggers(groups)),
			groups
		);
	}

	private static SimpleLogger[] getLoggers(Enum...groups) {
		SimpleLogger[] retval = new SimpleLogger[groups.length];

		for (int i=0; i < groups.length; i++) {
			retval[i] = SimpleLogger.getLogger(groups[i].getClass());
		}

		return retval;
	}
}
