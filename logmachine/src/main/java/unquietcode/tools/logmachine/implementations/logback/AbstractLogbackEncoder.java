package unquietcode.tools.logmachine.implementations.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.encoder.Encoder;
import ch.qos.logback.core.encoder.EncoderBase;
import ch.qos.logback.core.status.Status;
import unquietcode.tools.logmachine.EventMetadata;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * The MDC gives us access to a string.
 * Use that string to lookup the real object.
 *
 * @author Ben Fagin
 * @version 07-07-2012
 */
public abstract class AbstractLogbackEncoder extends EncoderBase<ILoggingEvent> {
	public static final Map<String, EventMetadata> metadataMap = new HashMap<String, EventMetadata>();
	public static final String MDC_KEY = "__EVENT_METADATA";

	@Override
	public void doEncode(ILoggingEvent event) throws IOException {
		String lookupKey = event.getMDCPropertyMap().get(MDC_KEY);
		EventMetadata metadata = metadataMap.get(lookupKey);

		if (metadata == null) {
			throw new NullPointerException("Unexpected null value for event metadata.");
		}

		String data = doLayout(event, metadata);
		outputStream.write(data.getBytes());
		outputStream.flush();
	}

	@Override
	public void close() throws IOException {
		// nothing
	}

	protected abstract String doLayout(ILoggingEvent event, EventMetadata metadata);
}
