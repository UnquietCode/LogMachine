package unquietcode.tools.logmachine.core;

/**
 * @author Ben Fagin
 * @version 2013-04-08
 */
public interface LogMachineBuilders_when<T> extends LogMachineBuilders<T> {

	/**
	 * Begin logging a new event, but only if the conditional
	 * flag is 'falsey', being null or false.
	 *
	 * This call and all chained calls will effectively be
	 * no-op if the condition is not expressly true.
	 */
	LogMachineBuilders when(Boolean flag);
}
