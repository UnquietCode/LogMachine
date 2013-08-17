package unquietcode.tools.logmachine.core;

/**
 * @author Ben Fagin
 * @version 2013-04-08
 */
public interface LogMachineBuilders_when<T> extends LogMachineBuilders<T> {
	LogMachineBuilders when(Boolean flag);
}
