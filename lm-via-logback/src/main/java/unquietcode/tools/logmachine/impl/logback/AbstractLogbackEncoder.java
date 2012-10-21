package unquietcode.tools.logmachine.impl.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.encoder.EncoderBase;
import unquietcode.tools.logmachine.EventMetadata;
import unquietcode.tools.logmachine.Switchboard;

import java.io.IOException;

/**
 * The MDC gives us access to a string.
 * Use that string to lookup the real object.
 *
 * @author Ben Fagin
 * @version 07-07-2012
 */
public abstract class AbstractLogbackEncoder extends EncoderBase<ILoggingEvent> {

	@Override
	public void doEncode(ILoggingEvent event) throws IOException {
		String data = doLayout(event);
		outputStream.write(data.getBytes());
		outputStream.write("\n".getBytes());
		outputStream.flush();
	}

	@Override
	public void close() throws IOException {
		// nothing
	}

	public String doLayout(ILoggingEvent event) {
		String lookupKey = event.getMDCPropertyMap().get(Switchboard.MDC_KEY);
		EventMetadata metadata = Switchboard.get(lookupKey);

		if (metadata == null) {
			throw new NullPointerException("Unexpected null value for event metadata.");
		}

		return doLayout(event, metadata);
	}

	protected abstract String doLayout(ILoggingEvent event, EventMetadata metadata);
}