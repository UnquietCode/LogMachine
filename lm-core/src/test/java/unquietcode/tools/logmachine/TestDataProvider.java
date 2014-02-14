package unquietcode.tools.logmachine;

import org.junit.Test;
import unquietcode.tools.logmachine.core.DataProvider;
import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.impl.simple.SimpleLogMachine;
import unquietcode.tools.logmachine.test.AbstractLoggerTest;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestDataProvider extends AbstractLoggerTest {

	@Override
	protected String getLoggerName() {
		return TestDataProvider.class.getName();
	}

	@Override
	public LogMachine getLogMachine() {
		return new SimpleLogMachine(TestDataProvider.class);
	}

	@Override
	protected Level getLevel() {
		return Level.TRACE;
	}

	@Test
	public void testWithProvider() {
		DataProvider provider = new DataProvider() {
			public void addData(Map<String, Object> data) {
				// new
				data.put("one", 1);

				// existing, should be ignored
				data.put("two", -2);
			}
		};

		log.addDataProvider(provider);
		log.with("two", 2).warn("test ({:two})");

		LogEvent event = getSingleEvent();
		assertEquals(1, event.getData().get("one"));
		assertEquals(2, event.getData().get("two"));
		assertEquals("expected correct message", "test (2)", event.getFormattedMessage());
	}
}
