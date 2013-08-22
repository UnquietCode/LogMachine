package unquietcode.tools.logmachine;

import org.junit.Test;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.core.topics.Topic;
import unquietcode.tools.logmachine.impl.simple.SimpleLogMachine;
import unquietcode.tools.logmachine.test.AbstractLoggerTest;

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
			void orange() {
				LogEvent event = getSingleEvent();
				List<Topic> groups = event.getGroups();
				assertEquals(2, groups.size());
				assertTrue(groups.contains(PrimaryColor.Red));
				assertTrue(groups.contains(PrimaryColor.Yellow));
				assertEquals("Orange", event.getMessage());
			}

			void green() {
				LogEvent event = getSingleEvent();
				List<Topic> groups = event.getGroups();
				assertEquals(2, groups.size());
				assertTrue(groups.contains(PrimaryColor.Blue));
				assertTrue(groups.contains(PrimaryColor.Yellow));
				assertEquals("Green", event.getMessage());
			}

			void purple() {
				LogEvent event = getSingleEvent();
				List<Topic> groups = event.getGroups();
				assertEquals(2, groups.size());
				assertTrue(groups.contains(PrimaryColor.Red));
				assertTrue(groups.contains(PrimaryColor.Blue));
				assertEquals("Purple", event.getMessage());
			}
		}

		Mixer mixer = new Mixer();
		lm.warn().to(PrimaryColor.Red, PrimaryColor.Yellow).send("Orange");
		mixer.orange();

		lm.error().to(PrimaryColor.Blue, PrimaryColor.Yellow).send("Green");
		mixer.green();

		lm.to(PrimaryColor.Red, PrimaryColor.Blue).info("Purple");
		mixer.purple();
	}

	@Test
	public void testHeterogeneousEnums() {
		lm.to(PrimaryColor.Red, PrimaryColor.Yellow, SecondaryColor.Orange).info("Orange");
		LogEvent event = getSingleEvent();
		List<Topic> groups = event.getGroups();
		assertEquals(3, groups.size());
		assertTrue(groups.contains(PrimaryColor.Red));
		assertTrue(groups.contains(PrimaryColor.Yellow));
		assertTrue(groups.contains(SecondaryColor.Orange));
	}

	@Test
	public void testDataPoints() {
		lm.to(PrimaryColor.Red, PrimaryColor.Blue)
		  .with("color", SecondaryColor.Purple.name())
		  .debug("{~1} {} {~2} {=>} {:color}", "+");

		LogEvent event = getSingleEvent();
		List<Topic> groups = event.getGroups();
		assertEquals(2, groups.size());
		assertTrue(groups.contains(PrimaryColor.Red));
		assertTrue(groups.contains(PrimaryColor.Blue));
		assertEquals(SecondaryColor.Purple.name(), event.getData().get("color"));
		assertEquals("Red + Blue {=>} Purple", event.getFormattedMessage());
	}

	@Test
	public void testAutomaticSource() {
		lm.from().info("Where you at dawg?");
		LogEvent event = getSingleEvent();
		assertEquals("testAutomaticSource", event.getLocation());
	}
}
