package unquietcode.tools.logmachine;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Benjamin Fagin
 * @version 02-19-2012
 *
 * Pedantic, but it works.
 */
public class Level_T {
	
	@Test
	public void greaterThan() {
		assertFalse(Level.ERROR.isGreaterThan(Level.ERROR));
		assertTrue(Level.ERROR.isGreaterThan(Level.WARN));
		assertTrue(Level.ERROR.isGreaterThan(Level.INFO));
		assertTrue(Level.ERROR.isGreaterThan(Level.DEBUG));
		assertTrue(Level.ERROR.isGreaterThan(Level.TRACE));

		assertFalse(Level.WARN.isGreaterThan(Level.ERROR));
		assertFalse(Level.WARN.isGreaterThan(Level.WARN));
		assertTrue(Level.WARN.isGreaterThan(Level.INFO));
		assertTrue(Level.WARN.isGreaterThan(Level.DEBUG));
		assertTrue(Level.WARN.isGreaterThan(Level.TRACE));

		assertFalse(Level.INFO.isGreaterThan(Level.ERROR));
		assertFalse(Level.INFO.isGreaterThan(Level.WARN));
		assertFalse(Level.INFO.isGreaterThan(Level.INFO));
		assertTrue(Level.INFO.isGreaterThan(Level.DEBUG));
		assertTrue(Level.INFO.isGreaterThan(Level.TRACE));

		assertFalse(Level.DEBUG.isGreaterThan(Level.ERROR));
		assertFalse(Level.DEBUG.isGreaterThan(Level.WARN));
		assertFalse(Level.DEBUG.isGreaterThan(Level.INFO));
		assertFalse(Level.DEBUG.isGreaterThan(Level.DEBUG));
		assertTrue(Level.DEBUG.isGreaterThan(Level.TRACE));

		assertFalse(Level.TRACE.isGreaterThan(Level.ERROR));
		assertFalse(Level.TRACE.isGreaterThan(Level.WARN));
		assertFalse(Level.TRACE.isGreaterThan(Level.INFO));
		assertFalse(Level.TRACE.isGreaterThan(Level.DEBUG));
		assertFalse(Level.TRACE.isGreaterThan(Level.TRACE));
	}
	
	@Test
	public void lessThan() {
		assertFalse(Level.ERROR.isLessThan(Level.ERROR));
		assertFalse(Level.ERROR.isLessThan(Level.WARN));
		assertFalse(Level.ERROR.isLessThan(Level.INFO));
		assertFalse(Level.ERROR.isLessThan(Level.DEBUG));
		assertFalse(Level.ERROR.isLessThan(Level.TRACE));

		assertTrue(Level.WARN.isLessThan(Level.ERROR));
		assertFalse(Level.WARN.isLessThan(Level.WARN));
		assertFalse(Level.WARN.isLessThan(Level.INFO));
		assertFalse(Level.WARN.isLessThan(Level.DEBUG));
		assertFalse(Level.WARN.isLessThan(Level.TRACE));

		assertTrue(Level.INFO.isLessThan(Level.ERROR));
		assertTrue(Level.INFO.isLessThan(Level.WARN));
		assertFalse(Level.INFO.isLessThan(Level.INFO));
		assertFalse(Level.INFO.isLessThan(Level.DEBUG));
		assertFalse(Level.INFO.isLessThan(Level.TRACE));

		assertTrue(Level.DEBUG.isLessThan(Level.ERROR));
		assertTrue(Level.DEBUG.isLessThan(Level.WARN));
		assertTrue(Level.DEBUG.isLessThan(Level.INFO));
		assertFalse(Level.DEBUG.isLessThan(Level.DEBUG));
		assertFalse(Level.DEBUG.isLessThan(Level.TRACE));

		assertTrue(Level.TRACE.isLessThan(Level.ERROR));
		assertTrue(Level.TRACE.isLessThan(Level.WARN));
		assertTrue(Level.TRACE.isLessThan(Level.INFO));
		assertTrue(Level.TRACE.isLessThan(Level.DEBUG));
		assertFalse(Level.TRACE.isLessThan(Level.TRACE));
	}	
}
