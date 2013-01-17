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
		assertTrue(Level.ERROR.isCoarserOrEqual(Level.ERROR));
		assertTrue(Level.ERROR.isCoarserOrEqual(Level.WARN));
		assertTrue(Level.ERROR.isCoarserOrEqual(Level.INFO));
		assertTrue(Level.ERROR.isCoarserOrEqual(Level.DEBUG));
		assertTrue(Level.ERROR.isCoarserOrEqual(Level.TRACE));

		assertFalse(Level.WARN.isCoarserOrEqual(Level.ERROR));
		assertTrue(Level.WARN.isCoarserOrEqual(Level.WARN));
		assertTrue(Level.WARN.isCoarserOrEqual(Level.INFO));
		assertTrue(Level.WARN.isCoarserOrEqual(Level.DEBUG));
		assertTrue(Level.WARN.isCoarserOrEqual(Level.TRACE));

		assertFalse(Level.INFO.isCoarserOrEqual(Level.ERROR));
		assertFalse(Level.INFO.isCoarserOrEqual(Level.WARN));
		assertTrue(Level.INFO.isCoarserOrEqual(Level.INFO));
		assertTrue(Level.INFO.isCoarserOrEqual(Level.DEBUG));
		assertTrue(Level.INFO.isCoarserOrEqual(Level.TRACE));

		assertFalse(Level.DEBUG.isCoarserOrEqual(Level.ERROR));
		assertFalse(Level.DEBUG.isCoarserOrEqual(Level.WARN));
		assertFalse(Level.DEBUG.isCoarserOrEqual(Level.INFO));
		assertTrue(Level.DEBUG.isCoarserOrEqual(Level.DEBUG));
		assertTrue(Level.DEBUG.isCoarserOrEqual(Level.TRACE));

		assertFalse(Level.TRACE.isCoarserOrEqual(Level.ERROR));
		assertFalse(Level.TRACE.isCoarserOrEqual(Level.WARN));
		assertFalse(Level.TRACE.isCoarserOrEqual(Level.INFO));
		assertFalse(Level.TRACE.isCoarserOrEqual(Level.DEBUG));
		assertTrue(Level.TRACE.isCoarserOrEqual(Level.TRACE));
	}
	
	@Test
	public void greaterThanOrEqual() {
		assertTrue(Level.ERROR.isFinerOrEqual(Level.ERROR));
		assertFalse(Level.ERROR.isFinerOrEqual(Level.WARN));
		assertFalse(Level.ERROR.isFinerOrEqual(Level.INFO));
		assertFalse(Level.ERROR.isFinerOrEqual(Level.DEBUG));
		assertFalse(Level.ERROR.isFinerOrEqual(Level.TRACE));

		assertTrue(Level.WARN.isFinerOrEqual(Level.ERROR));
		assertTrue(Level.WARN.isFinerOrEqual(Level.WARN));
		assertFalse(Level.WARN.isFinerOrEqual(Level.INFO));
		assertFalse(Level.WARN.isFinerOrEqual(Level.DEBUG));
		assertFalse(Level.WARN.isFinerOrEqual(Level.TRACE));

		assertTrue(Level.INFO.isFinerOrEqual(Level.ERROR));
		assertTrue(Level.INFO.isFinerOrEqual(Level.WARN));
		assertTrue(Level.INFO.isFinerOrEqual(Level.INFO));
		assertFalse(Level.INFO.isFinerOrEqual(Level.DEBUG));
		assertFalse(Level.INFO.isFinerOrEqual(Level.TRACE));

		assertTrue(Level.DEBUG.isFinerOrEqual(Level.ERROR));
		assertTrue(Level.DEBUG.isFinerOrEqual(Level.WARN));
		assertTrue(Level.DEBUG.isFinerOrEqual(Level.INFO));
		assertTrue(Level.DEBUG.isFinerOrEqual(Level.DEBUG));
		assertFalse(Level.DEBUG.isFinerOrEqual(Level.TRACE));

		assertTrue(Level.TRACE.isFinerOrEqual(Level.ERROR));
		assertTrue(Level.TRACE.isFinerOrEqual(Level.WARN));
		assertTrue(Level.TRACE.isFinerOrEqual(Level.INFO));
		assertTrue(Level.TRACE.isFinerOrEqual(Level.DEBUG));
		assertTrue(Level.TRACE.isFinerOrEqual(Level.TRACE));
	}	
}
