package unquietcode.tools.logmachine.test;

import org.junit.Assert;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * @author Ben Fagin
 * @version 10-21-2012
 */
public class AssertionStream extends PrintStream {
	public AssertionStream() {
		super(new ByteArrayOutputStream());
	}

	public void assertEquals(String test, String message) {
		String _data = readStream();
		String _message = message != null ? message : "";
		Assert.assertEquals(_message, test, _data);
	}

	public void assertContains(String test, String message) {
		String _data = readStream();
		String _message = message != null ? message : "";
		Assert.assertTrue(_message, _data.contains(test));
	}

	public void assertStartsWith(String test, String message) {
		String _data = readStream();
		String _message = message != null ? message : "";
		Assert.assertTrue(_message, _data.startsWith(test));
	}

	public void assertEndsWith(String test, String message) {
		String _data = readStream();
		String _message = message != null ? message : "";
		Assert.assertTrue(_message, _data.endsWith(test));
	}

	public void clear() {
		((ByteArrayOutputStream) out).reset();
	}

	private String readStream() {
		ByteArrayOutputStream oStream = (ByteArrayOutputStream) out;
		return new String(oStream.toByteArray());
	}
}
