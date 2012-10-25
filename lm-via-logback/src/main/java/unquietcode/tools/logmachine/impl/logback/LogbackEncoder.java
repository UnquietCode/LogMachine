package unquietcode.tools.logmachine.impl.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.encoder.EncoderBase;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.Switchboard;
import unquietcode.tools.logmachine.core.formats.Format;
import unquietcode.tools.logmachine.core.formats.PlaintextFormat;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Logback encoder which can process events through the MDC. This can be
 * used to plug enhanced message formatting to existing logback appenders
 * which accept formats.
 *
 * @author Ben Fagin
 * @version 07-07-2012
 */
public class LogbackEncoder extends EncoderBase<ILoggingEvent> {
	private Format format = new PlaintextFormat();

	void setFormat(Format format) {
		this.format = checkNotNull(format, "Format cannot be null.");
	}

	@Override
	public void doEncode(ILoggingEvent event) throws IOException {
		String lookupKey = event.getMDCPropertyMap().get(Switchboard.MDC_KEY);
		LogEvent _event = Switchboard.get(lookupKey);
		String data = _event != null ? format.format(_event) : null;

		// (formatter could have still returned null)
		if (data == null) {
			return;
		}

		outputStream.write(data.getBytes());
		outputStream.write("\n".getBytes());
		outputStream.flush();
	}

	@Override
	public void close() throws IOException {
		// nothing
	}
}
