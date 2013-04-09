package unquietcode.tools.logmachine.impl.elasticsearch.test;

import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.junit.BeforeClass;
import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.impl.elasticsearch.ElasticSearchAppender;
import unquietcode.tools.logmachine.impl.simple.SimpleLogMachine;
import unquietcode.tools.logmachine.impl.simple.SimpleLogger;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Ben Fagin
 * @version 10-25-2012
 */
public class ElasticSearchTestGenerator {
	private static LogMachine log;

	enum Color {
		Blue, Red, Yellow, Green
	}


	@BeforeClass
	public static void setup() {
		SimpleLogger logger = SimpleLogger.getLogger(ElasticSearchTestGenerator.class);
		ElasticSearchAppender appender = new ElasticSearchAppender();
		appender.setBatchThreshold(Level.TRACE);
		appender.setServers(Arrays.asList(new InetSocketTransportAddress("localhost", 9300)));

		logger.addAppender(appender);
		appender.start();
		log = new SimpleLogMachine(logger);
	}

	//@Test // don't run this normally
	public void serverTest() {
		for (int i=0; i < 150; ++i) {
			try { Thread.sleep(400); }
			catch (Exception ex) { throw new RuntimeException(ex); }
			Random gen = new Random();

			switch (gen.nextInt(20)) {
				case 0: log.from("method").debug("hi"); break;
				case 1: log.to(Color.Red, Color.Blue).info("hello");
				case 2: log.to(Color.Red, Color.Blue).info("hello"); break;
				case 3: log.info("hello {}", "world"); break;
				case 4: log.to(Color.Red, Color.Yellow).from("basic()").info("greetings");
				case 5: log.because(new RuntimeException("oh no, not again", new NullPointerException("null pointer"))).error("goodbye!");
				case 6: log.because(new RuntimeException("oh no, not again", new NullPointerException("null pointer")))
						   .from("serverTest()")
						   .with("count", i)
						   .with("reason", "Because being alone sucks.")
						   .to(Color.Blue)
						   .error("Why am I always so {~0}? {:reason}");
				break;
			}
		}
	}
}
