package unquietcode.tools.logmachine.impl.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.encoder.Encoder;
import ch.qos.logback.core.encoder.EncoderBase;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.Switchboard;
import unquietcode.tools.logmachine.core.formats.Format;
import unquietcode.tools.logmachine.core.formats.PlaintextFormat;

import java.io.IOException;
import java.io.OutputStream;

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
	private Encoder<ILoggingEvent> fallbackEncoder;


	public void setFallbackEncoder(Encoder<ILoggingEvent> fallbackEncoder) {
		this.fallbackEncoder = checkNotNull(fallbackEncoder, "encoder cannot be null");
	}

	public void setFormat(Format format) {
		this.format = checkNotNull(format, "Format cannot be null.");
	}

	@Override
	public void doEncode(ILoggingEvent event) throws IOException {
		String lookupKey = event.getMDCPropertyMap().get(Switchboard.MDC_KEY);
		LogEvent _event = Switchboard.get(lookupKey);
		StringBuilder data = _event != null ? format.format(_event) : null;

		// (formatter could have still returned null)
		if (data == null) {
			if (fallbackEncoder != null) {
				fallbackEncoder.doEncode(event);
				return;
			} else {
				return;
			}
		}

		data.append("\n");
		outputStream.write(data.toString().getBytes());
		outputStream.flush();
	}

	@Override
	public void init(OutputStream os) throws IOException {
		super.init(os);

		if (fallbackEncoder != null) {
			fallbackEncoder.init(os);
		}
	}

	@Override
	public void setContext(Context context) {
		super.setContext(context);

		if (fallbackEncoder != null) {
			fallbackEncoder.setContext(context);
		}
	}

	@Override
	public void close() throws IOException {
		if (fallbackEncoder != null) {
			fallbackEncoder.close();
		}
	}

	@Override
	public void start() {
		super.start();

		if (fallbackEncoder != null) {
			fallbackEncoder.start();
		}
	}

	@Override
	public void stop() {
		super.stop();

		if (fallbackEncoder != null) {
			fallbackEncoder.stop();
		}
	}
}
