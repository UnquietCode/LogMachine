package unquietcode.tools.logmachine.impl.elasticsearch;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Ben Fagin
 * @version 10-25-2012
 */
public class LogstashFormatTest {
	LogstashFormat fmt = new LogstashFormat();



	@Ignore("FIXME more like WRITEME, lolz")
	@Test
	public void test() {
		LogstashEvent event = new LogstashEvent();
		event.setTimestamp(System.currentTimeMillis());
		event.setMessage("hello");
		event.setSource("source");
		event.setType("lm");


	}
}
