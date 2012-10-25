package unquietcode.tools.logmachine.impl.web;

import org.junit.BeforeClass;
import org.junit.Test;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.impl.simple.SimpleLogMachine;
import unquietcode.tools.logmachine.impl.simple.SimpleLogger;

import java.util.Random;

/**
 * LogMachine backed by SLF4J.
 *
 * @author Ben Fagin
 * @version 05-16-2012
 */
public class LogServerTestGenerator {
	private static LogMachine log;

	enum Color {
		Blue, Red, Yellow, Green
	}


	@BeforeClass
	public static void setup() {
		SimpleLogger logger = SimpleLogger.getLogger(LogServerTestGenerator.class);
		LogServerAppender appender = new LogServerAppender();
		logger.addAppender(appender);
		appender.start();
		log = new SimpleLogMachine(logger);
	}


	@Test // don't run this normally
	public void serverTest() {
		for (int i=0; i < 150; ++i) {
			try { Thread.sleep(1000); }
			catch (Exception ex) { throw new RuntimeException(ex); }
			Random gen = new Random();

			switch (gen.nextInt(10)) {
				case 0: log.from("method").debug("hi"); break;
				case 1: log.to(Color.Red, Color.Blue).info("hello");
				case 2: log.to(Color.Red, Color.Blue).info("hello"); break;
				case 3: log.info("hello {}", "world"); break;
				case 4: log.to(Color.Red, Color.Yellow).from("basic()").info("greetings");
				case 5: log.because(new RuntimeException("oh no, not again", new NullPointerException("null pointer"))).error("goodbye!");
			}
		}
	}
}
