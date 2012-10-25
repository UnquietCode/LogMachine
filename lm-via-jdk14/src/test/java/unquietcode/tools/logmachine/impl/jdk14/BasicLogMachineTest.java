package unquietcode.tools.logmachine.impl.jdk14;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import unquietcode.tools.logmachine.TestBasicLogMachine;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.core.appenders.PersistentLogAppender;

/**
 * @author Ben Fagin
 * @version 10-24-2012
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({BasicLogMachineTest.TestBasicLogMachine_Proxy.class})
public class BasicLogMachineTest {

	public static class TestBasicLogMachine_Proxy extends TestBasicLogMachine {
		private static final AbstractLoggerTest proxy = new AbstractLoggerTest() {

			@Override
			protected String getLoggerName() {
				return TestBasicLogMachine.class.getName();
			}

			@Override
			public LogMachine getLogMachine() {
				return new JDKLogMachine(TestBasicLogMachine.class);
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

