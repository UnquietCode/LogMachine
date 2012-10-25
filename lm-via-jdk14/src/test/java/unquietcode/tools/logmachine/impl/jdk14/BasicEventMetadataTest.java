package unquietcode.tools.logmachine.impl.jdk14;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import unquietcode.tools.logmachine.TestEventMetadata;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.core.appenders.PersistentLogAppender;

/**
 * @author Ben Fagin
 * @version 10-24-2012
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({BasicEventMetadataTest.TestEventMetadata_Proxy.class})
public class BasicEventMetadataTest {

	public static class TestEventMetadata_Proxy extends TestEventMetadata {
		private static final AbstractLoggerTest proxy = new AbstractLoggerTest() {

			@Override
			protected String getLoggerName() {
				return TestEventMetadata.class.getName();
			}

			@Override
			public LogMachine getLogMachine() {
				return new JDKLogMachine(TestEventMetadata.class);
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

