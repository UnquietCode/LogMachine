package unquietcode.tools.logmachine.test;

import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.impl.simple.SimpleLogMachine;
import unquietcode.tools.logmachine.impl.simple.SimpleLogger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Ben Fagin
 * @version 2013-04-08
 */
public class ProxyBenchmark {

	//@Test
	public void run() {
		SimpleLogger logger = SimpleLogger.getLogger("benchmark");
		LogMachine lm = new SimpleLogMachine(logger);
		logger.setLevel(Level.TRACE);

		final int repetitions = 10000;
		long start, end;
		List<Long> timings = new ArrayList<Long>();
		double averageWithout, averageWith;


		// warmup
		lm.info("start");
		lm.info().send("start");


		// test without
		for (int i=0; i < repetitions; ++i) {
			Level level = randomLevel();

			start = System.nanoTime();
			switch (level) {
				case ERROR: lm.error("error"); break;
				case WARN:  lm.warn("warn"); break;
				case INFO:  lm.info("info"); break;
				case DEBUG: lm.debug("debug"); break;
				case TRACE: lm.trace("trace"); break;
			}
			end = System.nanoTime();
			timings.add(end - start);
		}

		averageWithout = computeAverage(timings);
		timings.clear();


		// test with proxu
		for (int i=0; i < 10000; ++i) {
			Level level = randomLevel();

			start = System.nanoTime();
			switch (level) {
				case ERROR: lm.error().send("error"); break;
				case WARN:  lm.warn().send("warn"); break;
				case INFO:  lm.info().send("info"); break;
				case DEBUG: lm.debug().send("debug"); break;
				case TRACE: lm.trace().send("trace"); break;
			}
			end = System.nanoTime();
			timings.add(end-start);
		}

		averageWith = computeAverage(timings);


		// results
		System.out.println("Without: "+averageWithout);
		System.out.println("With: "+averageWith);
	}

	private static double computeAverage(List<Long> timings) {
		BigDecimal sum = BigDecimal.ZERO;

		for (Long timing : timings) {
			sum = sum.add(BigDecimal.valueOf(timing));
		}

		return sum.divide(BigDecimal.valueOf(timings.size())).doubleValue();
	}

	private static final Random random = new Random();

	private static Level randomLevel() {
		switch (random.nextInt(5)) {
			case 0 : return Level.ERROR;
			case 1 : return Level.WARN;
			case 2 : return Level.INFO;
			case 3 : return Level.DEBUG;
			case 4 : return Level.TRACE;
		}

		return null;
	}
}
