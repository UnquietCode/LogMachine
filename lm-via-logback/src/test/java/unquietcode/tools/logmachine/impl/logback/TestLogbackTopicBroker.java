package unquietcode.tools.logmachine.impl.logback;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.core.formats.ShorterPlaintextFormat;
import unquietcode.tools.logmachine.core.topics.Topic;
import unquietcode.tools.logmachine.impl.slf4j.SLF4JLogMachine;
import unquietcode.tools.logmachine.test.AssertionStream;

import java.io.PrintStream;

/**
 * @author Ben Fagin
 * @version 10-26-12
 */
public class TestLogbackTopicBroker {

	@Test
	public void testTopicBrokerWithFilter() {
		Logger log = (Logger) LoggerFactory.getLogger("testTopicBrokerWithFiltering");
		LogMachine lm = new SLF4JLogMachine(log);

		AssertionStream stream = new AssertionStream();
		PrintStream out = System.out;
		System.setOut(stream);

		ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<ILoggingEvent>();
		appender.setContext(log.getLoggerContext());

		PatternLayoutEncoder fallbackEncoder = new PatternLayoutEncoder();
		fallbackEncoder.setContext(log.getLoggerContext());
		fallbackEncoder.setPattern("fallback: %m%n");
		fallbackEncoder.start();

		LogbackEncoder encoder = new LogbackEncoder();
		encoder.setFormat(new ShorterPlaintextFormat());
		encoder.setFallbackEncoder(fallbackEncoder);
		encoder.setContext(log.getLoggerContext());
		encoder.start();
		appender.setEncoder(encoder);

		LogbackDeduplicatingFilter<ILoggingEvent> filter = new LogbackDeduplicatingFilter<ILoggingEvent>();
		filter.setContext(log.getLoggerContext());
		appender.addFilter(filter);
		filter.start();

		log.addAppender(appender);
		appender.start();

		LogbackTopicBroker.subscribe(appender, X.TWO);

		lm.info("should always print");
		stream.assertEquals("[INFO] - should always print\n", "expected exact message printed");
		stream.clear();

		lm.to(X.ONE, X.TWO).info("should print once");
		stream.assertEquals("[INFO] [ONE | TWO] - should print once\n", "expected exact message printed");
		stream.clear();

		lm.to(X.TWO).info("should also print once");
		stream.assertEquals("[INFO] [TWO] - should also print once\n", "expected exact message printed");
		stream.clear();

		log.info("should print once via fallback");
		stream.assertEquals("fallback: should print once via fallback\n", "expected exact message printed");
		stream.clear();
	}

	enum X implements Topic {
		ONE, TWO
	}
}
