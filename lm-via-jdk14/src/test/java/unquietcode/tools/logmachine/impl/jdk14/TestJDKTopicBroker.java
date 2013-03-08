package unquietcode.tools.logmachine.impl.jdk14;

import org.junit.Test;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.core.formats.Format;
import unquietcode.tools.logmachine.core.formats.ShorterPlaintextFormat;
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
		Logger log = Logger.getLogger("testTopicBrokerWithFiltering");
		LogMachine lm = new JDKLogMachine(log);

		Logger root = Logger.getLogger("");
		PrintStream err = System.err;
		AssertionStream stream = new AssertionStream();

		System.setErr(stream);
		ConsoleHandler handler = new ConsoleHandler();
		System.setErr(err);
		root.addHandler(handler);

		JDKFormatter formatter = new JDKFormatter();
		Format fmt = new ShorterPlaintextFormat();
		formatter.setFormat(fmt);
		formatter.setFallbackFormater(handler.getFormatter());

		handler.setFormatter(formatter);
		handler.setFilter(new JDKDeduplicatingFilter());
		JDKTopicBroker.subscribe(handler, X.TWO);


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
	}

	enum X {
		ONE, TWO
	}
}
