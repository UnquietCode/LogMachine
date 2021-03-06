package unquietcode.tools.logmachine;

/**
 * @author Ben Fagin
 * @version 02-18-2013
 */
public abstract class LazyString {
	private boolean loaded = false;  // helps deal with null values
	private String string;

	protected abstract String _getString();

	public final String getString() {
		if (loaded) {
			return string;
		}

		synchronized (this) {
			if (!loaded) {
				string = _getString();
				loaded = true;
			}
		}

		return string;
	}

	public @Override String toString() {
		return getString();
	}
}
