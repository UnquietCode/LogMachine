package unquietcode.tools.logmachine;

/**
 * @author Ben Fagin
 * @version 05-16-2012
 *
 * Stores data in a tree structure, accessible using string keys which are
 * split along a predetermined delimiter. Uses string.split(...)
 */
public class SplitTree<T> {
	private final String delimiter;

	public SplitTree(String delimiter) {
		if (delimiter == null || delimiter.isEmpty()) {
			throw new IllegalArgumentException("You must provide a non-null, non-empty string.");
		}

		this.delimiter = delimiter;
	}


	public T get(String key) {
		if (key == null) {
			throw new IllegalArgumentException("key must not be null. (Did you mean to use \"\"?)");
		} else {
			key = key.trim();
		}

		String[] parts = key.split(delimiter);

		return null;
	}

	private static class Node<T> {

	}
}
