package unquietcode.tools.logmachine.impl.log4j;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import unquietcode.tools.logmachine.TestEventMetadata;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.core.appenders.PersistentLogAppender;

/**
 * @author Ben Fagin
 * @version 10-26-2012
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({BasicEventMetadataTest.TestEventMetadata_Proxy.class})
public class BasicEventMetadataTest {

	public static class TestEventMetadata_Proxy extends TestEventMetadata {
		private static final AbstractLog4jTest proxy = new AbstractLog4jTest() {

			@Override
			protected String getLoggerName() {
				return TestEventMetadata.class.getName();
			}

			@Override
			public LogMachine getLogMachine() {
				return new Log4jLogMachine(TestEventMetadata.class);
			}
		};

		@Override
		public LogMachine getLogMachine() {
			return proxy.getLogMachine();
		}

		@Before
		public @Override void _setup() {
			proxy._setup();
		}

		@Override
		public PersistentLogAppender getEventAppender() {
			return proxy.getEventAppender();
		}
	}
}

