package unquietcode.tools.logmachine.impl.jdk14;

import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.Switchboard;
import unquietcode.tools.logmachine.core.formats.Formatter;
import unquietcode.tools.logmachine.core.formats.PlaintextFormatter;

import java.util.logging.LogRecord;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Adaptor which allows log machine formats to be used as a formatter for
 * an existing {@link java.util.logging.Handler}.
 *
 * @author Ben Fagin
 * @version 10-24-2012
 */
public class JDKFormatter extends java.util.logging.Formatter {
	private Formatter formatter = new PlaintextFormatter();
	private java.util.logging.Formatter fallbackFormater;

	public void setFormatter(Formatter formatter) {
		this.formatter = checkNotNull(formatter, "formatter cannot be null");
	}

	public void setFallbackFormater(java.util.logging.Formatter formatter) {
		this.fallbackFormater = checkNotNull(formatter, "formatter cannot be null");
	}

	@Override
	public String format(LogRecord event) {
		String lookupKey = "_"+event.getSequenceNumber();
		LogEvent _event = Switchboard.get(lookupKey);
		StringBuilder data = _event != null ? formatter.format(_event) : null;

		if (data != null) {
			return data.append("\n").toString();
		} else if (fallbackFormater != null) {
			return fallbackFormater.format(event);
		} else {
			return "";
		}
	}
}
