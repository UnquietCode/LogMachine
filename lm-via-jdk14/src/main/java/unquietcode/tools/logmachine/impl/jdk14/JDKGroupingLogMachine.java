package unquietcode.tools.logmachine.impl.jdk14;

import unquietcode.tools.logmachine.core.GroupingLogHandler;
import unquietcode.tools.logmachine.core.GroupingLogMachine;

import java.util.logging.Logger;

/**
 * Primarily for testing. No hierarchies.
 *
 * @author Ben Fagin
 * @version 10-23-2012
 */
public class JDKGroupingLogMachine extends GroupingLogMachine<Logger> {
	private static final JDKHandler HANDLER = new JDKHandler();

	public JDKGroupingLogMachine(Enum... groups) {
		super(
				Logger.getLogger(GroupingLogHandler.GROUP_LOGGER),
			new GroupingLogHandler<Logger>(HANDLER, getLoggers(groups)),
			groups
		);
	}

	private static Logger[] getLoggers(Enum...groups) {
		Logger[] retval = new Logger[groups.length];

		for (int i=0; i < groups.length; i++) {
			retval[i] = Logger.getLogger(groups[i].getClass().getName());
		}

		return retval;
	}
}
