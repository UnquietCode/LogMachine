package unquietcode.tools.logmachine.impl.jdk14;

import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.Switchboard;
import unquietcode.tools.logmachine.core.formats.Format;
import unquietcode.tools.logmachine.core.formats.PlaintextFormat;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Adaptor which allows log machine to be used as a formatter for
 * an existing {@link java.util.logging.Handler}.
 *
 * @author Ben Fagin
 * @version 10-24-2012
 */
public class JDKFormatter extends Formatter {
	private Format format = new PlaintextFormat();

	public void setFormat(Format format) {
		this.format = checkNotNull(format, "format cannot be null");
	}

	@Override
	public String format(LogRecord event) {
		String lookupKey = "_"+event.getSequenceNumber();
		LogEvent _event = Switchboard.get(lookupKey);
		String data = _event != null ? format.format(_event) : null;

		// (formatter could have still returned null)
		return data == null ? "" : data;
	}
}
