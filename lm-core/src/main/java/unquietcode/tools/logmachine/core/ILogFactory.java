package unquietcode.tools.logmachine.core;

import unquietcode.tools.logmachine.LogMachine;

/**
 * Simple interface defining contract for LogMachine factory classes.
 *
 * @author Ben Fagin
 * @version 03-04-2012
 */
public interface ILogFactory<T> {
	LogMachine<T> getInstance(T logger);
}
