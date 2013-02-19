package unquietcode.tools.logmachine.impl.logback;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import unquietcode.tools.logmachine.test.AssertionStream;

import java.io.PrintStream;

/**
 * SLF4J without anything else.
 *
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class TestBasicLogback {
	private static final Logger log = (Logger) LoggerFactory.getLogger(TestBasicLogback.class);

	@Test
	public void testAssertionStream() {
		AssertionStream stream = new AssertionStream();
		PrintStream out = System.out;
		System.setOut(stream);

		PatternLayoutEncoder encoder = new PatternLayoutEncoder();
		encoder.setContext(log.getLoggerContext());
		encoder.setPattern("%m%n");
		encoder.start();

		ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<ILoggingEvent>();
		appender.setContext(log.getLoggerContext());
		appender.setEncoder(encoder);
		appender.start();
		log.addAppender(appender);

		log.info("Hello world.");
		System.setOut(out);
		stream.assertEquals("Hello world.\n", "expected exact message printed");
	}
}
