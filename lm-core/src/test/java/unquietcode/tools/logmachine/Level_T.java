package unquietcode.tools.logmachine;

import org.junit.Test;
import unquietcode.tools.logmachine.core.Level;

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
	public void lesserThanOrEqual() {
		assertTrue(Level.ERROR.isLesserOrEqual(Level.ERROR));
		assertTrue(Level.ERROR.isLesserOrEqual(Level.WARN));
		assertTrue(Level.ERROR.isLesserOrEqual(Level.INFO));
		assertTrue(Level.ERROR.isLesserOrEqual(Level.DEBUG));
		assertTrue(Level.ERROR.isLesserOrEqual(Level.TRACE));

		assertFalse(Level.WARN.isLesserOrEqual(Level.ERROR));
		assertTrue(Level.WARN.isLesserOrEqual(Level.WARN));
		assertTrue(Level.WARN.isLesserOrEqual(Level.INFO));
		assertTrue(Level.WARN.isLesserOrEqual(Level.DEBUG));
		assertTrue(Level.WARN.isLesserOrEqual(Level.TRACE));

		assertFalse(Level.INFO.isLesserOrEqual(Level.ERROR));
		assertFalse(Level.INFO.isLesserOrEqual(Level.WARN));
		assertTrue(Level.INFO.isLesserOrEqual(Level.INFO));
		assertTrue(Level.INFO.isLesserOrEqual(Level.DEBUG));
		assertTrue(Level.INFO.isLesserOrEqual(Level.TRACE));

		assertFalse(Level.DEBUG.isLesserOrEqual(Level.ERROR));
		assertFalse(Level.DEBUG.isLesserOrEqual(Level.WARN));
		assertFalse(Level.DEBUG.isLesserOrEqual(Level.INFO));
		assertTrue(Level.DEBUG.isLesserOrEqual(Level.DEBUG));
		assertTrue(Level.DEBUG.isLesserOrEqual(Level.TRACE));

		assertFalse(Level.TRACE.isLesserOrEqual(Level.ERROR));
		assertFalse(Level.TRACE.isLesserOrEqual(Level.WARN));
		assertFalse(Level.TRACE.isLesserOrEqual(Level.INFO));
		assertFalse(Level.TRACE.isLesserOrEqual(Level.DEBUG));
		assertTrue(Level.TRACE.isLesserOrEqual(Level.TRACE));
	}
	
	@Test
	public void greaterThanOrEqual() {
		assertTrue(Level.ERROR.isGreaterOrEqual(Level.ERROR));
		assertFalse(Level.ERROR.isGreaterOrEqual(Level.WARN));
		assertFalse(Level.ERROR.isGreaterOrEqual(Level.INFO));
		assertFalse(Level.ERROR.isGreaterOrEqual(Level.DEBUG));
		assertFalse(Level.ERROR.isGreaterOrEqual(Level.TRACE));

		assertTrue(Level.WARN.isGreaterOrEqual(Level.ERROR));
		assertTrue(Level.WARN.isGreaterOrEqual(Level.WARN));
		assertFalse(Level.WARN.isGreaterOrEqual(Level.INFO));
		assertFalse(Level.WARN.isGreaterOrEqual(Level.DEBUG));
		assertFalse(Level.WARN.isGreaterOrEqual(Level.TRACE));

		assertTrue(Level.INFO.isGreaterOrEqual(Level.ERROR));
		assertTrue(Level.INFO.isGreaterOrEqual(Level.WARN));
		assertTrue(Level.INFO.isGreaterOrEqual(Level.INFO));
		assertFalse(Level.INFO.isGreaterOrEqual(Level.DEBUG));
		assertFalse(Level.INFO.isGreaterOrEqual(Level.TRACE));

		assertTrue(Level.DEBUG.isGreaterOrEqual(Level.ERROR));
		assertTrue(Level.DEBUG.isGreaterOrEqual(Level.WARN));
		assertTrue(Level.DEBUG.isGreaterOrEqual(Level.INFO));
		assertTrue(Level.DEBUG.isGreaterOrEqual(Level.DEBUG));
		assertFalse(Level.DEBUG.isGreaterOrEqual(Level.TRACE));

		assertTrue(Level.TRACE.isGreaterOrEqual(Level.ERROR));
		assertTrue(Level.TRACE.isGreaterOrEqual(Level.WARN));
		assertTrue(Level.TRACE.isGreaterOrEqual(Level.INFO));
		assertTrue(Level.TRACE.isGreaterOrEqual(Level.DEBUG));
		assertTrue(Level.TRACE.isGreaterOrEqual(Level.TRACE));
	}	
}
