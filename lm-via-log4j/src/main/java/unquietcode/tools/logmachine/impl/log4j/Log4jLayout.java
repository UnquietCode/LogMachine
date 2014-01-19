package unquietcode.tools.logmachine.impl.log4j;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.Switchboard;
import unquietcode.tools.logmachine.core.formats.Formatter;
import unquietcode.tools.logmachine.core.formats.PlaintextFormatter;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Log4j layout which can process events through the MDC. This can be
 * used to plug enhanced message formatting to existing log4j appenders
 * which accept layouts.
 *
 * @author Ben Fagin
 * @version 10-26-2012
 */
public class Log4jLayout extends Layout {
	private Formatter formatter = new PlaintextFormatter();
	private Layout fallbackLayout;


	public void setFallbackLayout(Layout fallbackLayout) {
		this.fallbackLayout = checkNotNull(fallbackLayout, "layout cannot be null");
	}

	public void setFormatter(Formatter formatter) {
		this.formatter = checkNotNull(formatter, "Formatter cannot be null.");
	}

	@Override
	public String format(LoggingEvent event) {
		String lookupKey = (String) event.getMDC(Switchboard.MDC_KEY);
		LogEvent _event = Switchboard.get(lookupKey);
		StringBuilder data = _event != null ? formatter.format(_event) : null;

		if (data != null) {
			return data.append("\n").toString();
		} else if (fallbackLayout != null) {
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