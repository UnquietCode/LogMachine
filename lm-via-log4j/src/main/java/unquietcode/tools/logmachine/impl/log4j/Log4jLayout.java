package unquietcode.tools.logmachine.impl.log4j;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.Switchboard;
import unquietcode.tools.logmachine.core.formats.Format;
import unquietcode.tools.logmachine.core.formats.PlaintextFormat;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Logback encoder which can process events through the MDC. This can be
 * used to plug enhanced message formatting to existing logback appenders
 * which accept formats.
 *
 * @author Ben Fagin
 * @version 10-26-2012
 */
public class Log4jLayout extends Layout {
	private Format format = new PlaintextFormat();
	private Layout fallbackLayout;


	public void setFallbackLayout(Layout fallbackLayout) {
		this.fallbackLayout = checkNotNull(fallbackLayout, "layout cannot be null");
	}

	public void setFormat(Format format) {
		this.format = checkNotNull(format, "Format cannot be null.");
	}

	@Override
	public String format(LoggingEvent event) {
		String lookupKey = (String) event.getMDC(Switchboard.MDC_KEY);
		LogEvent _event = Switchboard.get(lookupKey);
		String data = _event != null ? format.format(_event) : null;

		// (formatter could have still returned null)
		if (data == null && fallbackLayout != null) {
			return fallbackLayout.format(event);
		} else {
			return "";
		}
	}

	@Override
	public boolean ignoresThrowable() {
		return false;
	}

	@Override
	public void activateOptions() {
		// nothing
	}
}
