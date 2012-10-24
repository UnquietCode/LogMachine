package unquietcode.tools.logmachine;

import org.junit.Test;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.impl.simple.SimpleLogFactory;
import unquietcode.tools.logmachine.impl.simple.SimpleLogger;
import unquietcode.tools.logmachine.test.AbstractLoggerTest;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Ben Fagin
 * @version 10-22-2012
 */
public class TestEventMetadata extends AbstractLoggerTest {
	private static final SimpleLogger log = SimpleLogger.getLogger(TestEventMetadata.class);
	private static final LogMachine lm = SimpleLogFactory.getLogMachine(log);

	enum PrimaryColor {
		Blue, Red, Yellow,
	}

	enum SecondaryColor {
		Green, Purple, Orange
	}

	@Override
	protected String getLoggerName() {
		return TestEventMetadata.class.getName();
	}


	@Test
	public void testEventCategories() {
		class Mixer {
			void orange() {
				LogEvent event = getSingleEvent();
				List<Enum> groups = event.getGroups();
				assertEquals(2, groups.size());
				assertTrue(groups.contains(PrimaryColor.Red));
				assertTrue(groups.contains(PrimaryColor.Yellow));
				assertEquals("Orange", event.getMessage());
			}

			void green() {
				LogEvent event = getSingleEvent();
				List<Enum> groups = event.getGroups();
				assertEquals(2, groups.size());
				assertTrue(groups.contains(PrimaryColor.Blue));
				assertTrue(groups.contains(PrimaryColor.Yellow));
				assertEquals("Green", event.getMessage());
			}

			void purple() {
				LogEvent event = getSingleEvent();
				List<Enum> groups = event.getGroups();
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
		List<Enum> groups = event.getGroups();
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
		List<Enum> groups = event.getGroups();
		assertEquals(2, groups.size());
		assertTrue(groups.contains(PrimaryColor.Red));
		assertTrue(groups.contains(PrimaryColor.Blue));
		assertEquals(SecondaryColor.Purple.name(), event.getData().get("color"));
		assertEquals("Red + Blue {=>} Purple", event.getFormattedMessage());
	}
}
