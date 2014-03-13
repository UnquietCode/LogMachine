package unquietcode.tools.logmachine.impl.slf4j;

import org.junit.Before;
import org.junit.Test;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.appenders.PersistentLogAppender;
import unquietcode.tools.logmachine.core.topics.Topic;
import unquietcode.tools.logmachine.core.topics.TopicBroker;
import unquietcode.tools.logmachine.test.AssertionStream;

import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static unquietcode.tools.logmachine.impl.slf4j.TestSLF4JTopics.Topics.*;

/**
 * Basic test that
 * @author Ben Fagin
 * @version 2014-01-22
 */
public class TestSLF4JTopics {

	final SLF4JLogMachine log = new SLF4JLogMachine(TestSLF4JTopics.class);
	final PersistentLogAppender appender = new PersistentLogAppender();
	final Exception exception = new Exception("oh no, not again");

	@Before
	public void setItUp() {
		TopicBroker.subscribe(appender, Postgres);
	}

	@Test
	public void test() {
		int userID = 1234;

		final PrintStream stdErr = System.err;
		AssertionStream stream = new AssertionStream();
		System.setErr(stream);

		try {
			doSomethingCrazy();
		} catch (Exception ex) {

			log.to(Postgres, User, Delete)
			   .because(ex)
			   .error("could not create user with id {@ id}", userID)
			;
		} finally {
			System.setErr(stdErr);
		}

		stream.assertContains(exception.getMessage(), "expected the exception to have been logged");

		assertEquals(1, appender.getAllEvents().size());
		LogEvent event = appender.getAllEvents().get(0);

		assertEquals("could not create user with id "+userID, event.getFormattedMessage());
		assertTrue(exception == event.getCause());

		// check topics
		assertEquals(3, event.getTopics().size());
		assertTrue(event.getTopics().contains(Postgres));
		assertTrue(event.getTopics().contains(User));
		assertTrue(event.getTopics().contains(Delete));
	}

	private void doSomethingCrazy() throws Exception {
		throw exception;
	}

	enum Topics implements Topic {
		Postgres, User, Database, Create, Delete, Redis
	}
}
