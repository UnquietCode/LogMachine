package unquietcode.tools.logmachine.impl.log4j;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.Test;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.core.formats.ShorterPlaintextFormat;
import unquietcode.tools.logmachine.core.topics.Topic;
import unquietcode.tools.logmachine.test.AssertionStream;

import java.io.PrintStream;

/**
 * @author Ben Fagin
 * @version 10-26-12
 */
public class TestLog4jTopicBroker {

	@Test
	public void testTopicBroker() {
		Logger log = Logger.getLogger("testTopicBroker");
		LogMachine lm = new Log4jLogMachine(log);

		AssertionStream stream = new AssertionStream();
		PrintStream out = System.out;
		System.setOut(stream);

		ConsoleAppender appender = new ConsoleAppender();
		Log4jLayout layout = new Log4jLayout();
		layout.setFallbackLayout(new PatternLayout());
		layout.setFormat(new ShorterPlaintextFormat());

		appender.setLayout(layout);
		appender.activateOptions();

		log.addAppender(appender);
		Log4jTopicBroker.subscribe(appender, X.TWO);

		lm.info("should always print");
		stream.assertEquals("[INFO] - should always print\n", "expected exact message printed");
		stream.clear();

		lm.to(X.ONE).info("should print once");
		stream.assertEquals("[INFO] [ONE] - should print once\n", "expected exact message printed");
		stream.clear();

		lm.to(X.TWO).info("should print twice");
		stream.assertEquals("[INFO] [TWO] - should print twice\n[INFO] [TWO] - should print twice\n", "expected exact message printed");
		stream.clear();

		System.setOut(out);
	}

	@Test
	public void testTopicBrokerWithFilter() {
		Logger log = Logger.getLogger("testTopicBrokerWithFiltering");
		LogMachine lm = new Log4jLogMachine(log);

		AssertionStream stream = new AssertionStream();
		PrintStream out = System.out;
		System.setOut(stream);

		ConsoleAppender appender = new ConsoleAppender();
		Log4jLayout layout = new Log4jLayout();
		layout.setFallbackLayout(new PatternLayout());
		layout.setFormat(new ShorterPlaintextFormat());
		appender.setLayout(layout);

		appender.addFilter(new Log4jDeduplicatingFilter());
		appender.activateOptions();

		log.addAppender(appender);
		Log4jTopicBroker.subscribe(appender, X.TWO);

		lm.info("should always print");
		stream.assertEquals("[INFO] - should always print\n", "expected exact message printed");
		stream.clear();

		lm.to(X.ONE, X.TWO).info("should print once");
		stream.assertEquals("[INFO] [ONE | TWO] - should print once\n", "expected exact message printed");
		stream.clear();

		lm.to(X.TWO).info("should also print once");
		stream.assertEquals("[INFO] [TWO] - should also print once\n", "expected exact message printed");
		stream.clear();

		log.info("should prince once via fallback");
		stream.assertEquals("should prince once via fallback\n", "expected exact message printed");
		stream.clear();

		System.setOut(out);
	}

	enum X implements Topic {
		ONE, TWO
	}
}
