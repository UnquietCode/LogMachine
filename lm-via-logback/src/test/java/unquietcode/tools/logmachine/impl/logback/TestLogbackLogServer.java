package unquietcode.tools.logmachine.impl.logback;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import unquietcode.tools.logmachine.LogMachine;
import unquietcode.tools.logmachine.core.ILogFactory;

import java.util.Random;

/**
 * LogMachine backed by SLF4J.
 *
 * @author Ben Fagin
 * @version 05-16-2012
 */
public class TestLogbackLogServer {
	private static LogMachine log = ILogFactory.getInstance(LoggerFactory.getLogger(TestLogbackLogServer.class));

	enum Color {
		Blue, Red, Yellow, Green
	}

	@Test
	public void serverTest() {

		for (int i=0; i < 150; ++i) {
			try { Thread.sleep(1000); }
			catch (Exception ex) { throw new RuntimeException(ex); }
			Random gen = new Random();

			switch (gen.nextInt(10)) {
				case 0: log.from("method").debug("hi"); break;
				case 1: log.to(Color.Red, Color.Blue).info("hello"); break;
				case 2: log.to(Color.Red, Color.Blue).info("hello"); break;
				case 3: log.info("hello {0}", "world"); break;
				case 4: log.to(Color.Red, Color.Yellow).from("basic()").info("greetings");
				case 5: log.because(new RuntimeException("oh no, not again", new NullPointerException("null pointer"))).error("goodbye!");
			}
		}
	}
}
