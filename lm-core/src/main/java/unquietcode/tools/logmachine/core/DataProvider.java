package unquietcode.tools.logmachine.core;

import java.util.Map;

/**
* @author Ben Fagin
* @version 2013-08-23
*/
public interface DataProvider {

	/**
	 * Add contextual data to a new log event in this thread.
	 *
	 * Note that any data already keyed in the event will
	 * override data provided here.
	 *
	 * @param data new data
	 */
	void addData(Map<String, Object> data);
}
