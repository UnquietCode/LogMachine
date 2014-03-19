package unquietcode.tools.logmachine;

import org.junit.Before;
import org.junit.Test;
import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.core.LoggingComponent;
import unquietcode.tools.logmachine.core.topics.Topic;
import unquietcode.tools.logmachine.core.topics.TopicBroker;
import unquietcode.tools.logmachine.impl.simple.SimpleLogMachine;
import unquietcode.tools.logmachine.impl.simple.SimpleLogger;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static unquietcode.tools.logmachine.TestTopics.What.Potatoes;
import static unquietcode.tools.logmachine.TestTopics.What.Tomatoes;

public class TestTopics {

	LogMachine log;

	@Before
	public void init() {
		SimpleLogger logger = SimpleLogger.getLogger(TestTopics.class);
		logger.setLevel(Level.TRACE);

		log = new SimpleLogMachine(logger);
	}

	@Test
	public void testThatTopicsRespondToLevels() {
		final AtomicInteger potatoes = new AtomicInteger();
		final AtomicInteger tomatoes = new AtomicInteger();

		TopicBroker.subscribe(new LoggingComponent() {
			public void handle(LogEvent event) {
				potatoes.incrementAndGet();
			}
		}, Potatoes);

		TopicBroker.subscribe(new LoggingComponent() {
			public void handle(LogEvent event) {
				tomatoes.incrementAndGet();
			}
		}, Tomatoes);


		// set the level too high
		TopicBroker.setLevel(Level.INFO, Potatoes);
		log.to(Tomatoes, Potatoes).trace("Oh. It's you.");

		assertEquals(0, potatoes.get());
		assertEquals(1, tomatoes.get());


		// clear the level setting
		TopicBroker.setLevel(null, Potatoes);
		log.to(Tomatoes, Potatoes).trace("Thank you for helping us help you help us all.");

		assertEquals(1, potatoes.get());
		assertEquals(2, tomatoes.get());
	}

	public enum What implements Topic {
		Potatoes, Tomatoes
	}
}
