package unquietcode.tools.logmachine.impl.elasticsearch;

import unquietcode.tools.logmachine.core.LogEvent;

/**
 * @author Ben Fagin
 * @version 2013-04-01
 */
public interface ElasticSearchJSONFormatter {
	byte[] format(LogEvent event);
	String getIndexName(LogEvent event);
}
