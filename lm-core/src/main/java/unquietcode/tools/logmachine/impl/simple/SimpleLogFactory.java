package unquietcode.tools.logmachine.impl.simple;

/**
 * @author Ben Fagin
 * @version 10-24-2012
 */
public class SimpleLogFactory {
	private SimpleLogFactory() { }


	public static SimpleLogMachine getLogMachine(SimpleLogger logger) {
		return new SimpleLogMachine(logger);
	}
}
