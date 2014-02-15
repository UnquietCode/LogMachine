package unquietcode.tools.logmachine;

import org.junit.Test;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.core.appenders.PersistentLogAppender;
import unquietcode.tools.logmachine.core.topics.Topic;
import unquietcode.tools.logmachine.core.topics.TopicLogMachine;
import unquietcode.tools.logmachine.impl.simple.SimpleLogMachine;
import unquietcode.tools.logmachine.test.AbstractLoggerTest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Ben Fagin
 * @version 10-22-2012
 */
public class TestEventMetadata extends AbstractLoggerTest {

	enum PrimaryColor implements Topic {
		Blue, Red, Yellow,
	}

	enum SecondaryColor implements Topic {
		Green, Purple, Orange
	}

	@Override
	protected String getLoggerName() {
		return TestEventMetadata.class.getName();
	}

	@Override
	public LogMachine getLogMachine() {
		return new SimpleLogMachine(TestEventMetadata.class);
	}

	@Test
	public void testEventCategories() {
		class Mixer {
			void assertColor(String name, PrimaryColor...colors) {
				LogEvent event = getSingleEvent();
				List<Topic> topics = event.getTopics();
				assertEquals(colors.length, topics.size());

				for (PrimaryColor color : colors) {
					assertTrue(topics.contains(color));
				}

				assertEquals(name, event.getMessage());
			}
		}

		final Mixer mixer = new Mixer();

		log.warn().to(PrimaryColor.Red, PrimaryColor.Yellow).send("Orange");
		mixer.assertColor("Orange", PrimaryColor.Yellow, PrimaryColor.Red);

		log.error().to(PrimaryColor.Blue, PrimaryColor.Yellow).send("Green");
		mixer.assertColor("Green", PrimaryColor.Yellow, PrimaryColor.Blue);

		log.to(PrimaryColor.Red, PrimaryColor.Blue).info("Purple");
		mixer.assertColor("Purple", PrimaryColor.Blue, PrimaryColor.Red);
	}

	@Test
	public void testDefaultTopics() {
		LogMachine log = new TopicLogMachine(PrimaryColor.Yellow, PrimaryColor.Blue);
		PersistentLogAppender appender = new PersistentLogAppender();
		log.addComponent(appender);

		log.to(PrimaryColor.Red).info("sup");
		final List<Topic> eventTopics = appender.getAllEvents().get(0).getTopics();

		assertEquals(3, eventTopics.size());
		assertTrue(eventTopics.contains(PrimaryColor.Yellow));
		assertTrue(eventTopics.contains(PrimaryColor.Blue));
		assertTrue(eventTopics.contains(PrimaryColor.Red));
	}

	@Test
	public void testHeterogeneousEnums() {
		log.to(PrimaryColor.Red, PrimaryColor.Yellow, SecondaryColor.Orange).info("Orange");

		LogEvent event = getSingleEvent();
		List<Topic> topics = event.getTopics();

		assertEquals(3, topics.size());
		assertTrue(topics.contains(PrimaryColor.Red));
		assertTrue(topics.contains(PrimaryColor.Yellow));
		assertTrue(topics.contains(SecondaryColor.Orange));
	}

	@Test
	public void testDataPoints() {
		String s = "+";

		log.to(PrimaryColor.Red, PrimaryColor.Blue)
		   .with("color", SecondaryColor.Purple.name())
		   .debug("{~1} {} {~2} {=>} {:color}", s);

		LogEvent event = getSingleEvent();
		List<Topic> topics = event.getTopics();

		assertEquals(2, topics.size());
		assertTrue(topics.contains(PrimaryColor.Red));
		assertTrue(topics.contains(PrimaryColor.Blue));
		assertEquals(SecondaryColor.Purple.name(), event.getData().get("color"));
		assertEquals("Red + Blue {=>} Purple", event.getFormattedMessage());
	}

	// keep this test at the bottom, it depends on line numbers

	@Test
	public void testLocationFormatting() {
		final String uri = "nope\t";
		getURI(uri);

		LogEvent event = getSingleEvent();
		assertEquals(uri, event.getData().get("uri"));

		final String name = TestEventMetadata.class.getSimpleName()+"#getURI:"+136;
		assertTrue(event.getLocation().contains(name));
	}

	public URI getURI(String uri) {
		try {
			return new URI(uri);
		} catch (URISyntaxException e) {
			log.because(e).fromHere()
			   .with("uri", e.getInput())
			   .warn("failed to create URI from string '{:uri}'");

			return null;
		}
	}
}
