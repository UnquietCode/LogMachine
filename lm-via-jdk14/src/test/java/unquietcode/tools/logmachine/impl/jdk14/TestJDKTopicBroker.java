package unquietcode.tools.logmachine.impl.jdk14;

import org.junit.Test;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.core.formats.Formatter;
import unquietcode.tools.logmachine.core.formats.ShorterPlaintextFormatter;
import unquietcode.tools.logmachine.core.topics.Topic;
import unquietcode.tools.logmachine.core.topics.TopicBroker;
import unquietcode.tools.logmachine.test.AssertionStream;

import java.io.PrintStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * @author Ben Fagin
 * @version 10-26-12
 */
public class TestJDKTopicBroker {

	@Test
	public void testTopicBrokerWithFilter() {
		// a logger and a log machine to wrap it
		Logger log = Logger.getLogger("myTestLogger");
		LogMachine lm = new JDKLogMachine(log);

		// swap out syserr and add a console handler to root
		Logger root = Logger.getLogger("");
		PrintStream err = System.err;
		AssertionStream stream = new AssertionStream();

		System.setErr(stream);
		ConsoleHandler handler = new ConsoleHandler();
		System.setErr(err);
		root.addHandler(handler);

		// use a custom formatter with fallback
		JDKFormatter formatter = new JDKFormatter();
		Formatter fmt = new ShorterPlaintextFormatter();
		formatter.setFormatter(fmt);
		formatter.setFallbackFormater(handler.getFormatter());
		handler.setFormatter(formatter);

		// subscribe an appender to a topic
		JDKLogMachine topicAppender = new JDKLogMachine();
		TopicBroker.subscribe(topicAppender, X.TWO);


		lm.info("should always print");
		stream.assertEquals("[INFO] - should always print\n", "expected exact message");
		stream.clear();

		lm.to(X.ONE, X.TWO).info("should print once");
		stream.assertEquals("[INFO] [ONE | TWO] - should print once\n", "expected exact message");
		stream.clear();

		lm.to(X.TWO).info("should also print once");
		stream.assertEquals("[INFO] [TWO] - should also print once\n", "expected exact message");
		stream.clear();

		log.info("should print with fallback once");
		stream.assertEndsWith("should print with fallback once\n", "expected exact message");
		stream.clear();

		//assertEquals("expected two topic events", 2, topicAppender.getAllEvents().size());
	}

	enum X implements Topic {
		ONE, TWO
	}
}