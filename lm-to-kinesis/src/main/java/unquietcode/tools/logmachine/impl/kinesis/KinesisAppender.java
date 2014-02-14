package unquietcode.tools.logmachine.impl.kinesis;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.services.kinesis.AmazonKinesisAsync;
import com.amazonaws.services.kinesis.AmazonKinesisAsyncClient;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LoggingComponent;
import unquietcode.tools.logmachine.core.formats.Formatter;
import unquietcode.tools.logmachine.core.formats.JSONFormatter;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Appender which submits log events to an AWS Kinesis stream.
 *
 * @author Ben Fagin
 * @version 2014-01-31
 */
public class KinesisAppender implements LoggingComponent {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
	private static final int ERROR_THRESHOLD = 100;

	private final AmazonKinesisAsync kinesis;
	private final String streamName;
	private final AtomicInteger errorCounter = new AtomicInteger(0);
	private Formatter formatter = new JSONFormatter();
	private boolean enabled = true;


	public KinesisAppender(AWSCredentials credentials, String streamName) {
		this(new StaticCredentialsProvider(credentials), streamName);
	}

	public KinesisAppender(AWSCredentialsProvider provider, String streamName) {
		checkArgument(streamName != null && !streamName.trim().isEmpty());
		this.streamName = streamName;

		this.kinesis = new AmazonKinesisAsyncClient(provider);
	}

	public void setFormatter(Formatter formatter) {
		this.formatter = checkNotNull(formatter);
	}

	protected String getPartition(LogEvent event) {
		return dateFormat.format(new Date(event.getTimestamp()));
	}

	@Override
	public void handle(LogEvent event) {
		if (!enabled) { return; }

		StringBuilder _data = formatter.format(event);
		byte[] data = _data.toString().getBytes(Charset.forName("UTF-8"));

		PutRecordRequest request = new PutRecordRequest()
			.withStreamName(streamName)
			.withPartitionKey(getPartition(event))
			.withData(ByteBuffer.wrap(data))
		;

		kinesis.putRecordAsync(request, new AsyncHandler<PutRecordRequest, PutRecordResult>() {
			public @Override void onError(Exception exception) {
				System.err.println("encountered an error while writing log data to the Kinesis stream");
				exception.printStackTrace();

				if (errorCounter.incrementAndGet() < ERROR_THRESHOLD) {
					return;
				}

				// disable ourselves for 5 minutes after crossing the error threshold

				synchronized (errorCounter) {
					if (errorCounter.incrementAndGet() >= ERROR_THRESHOLD) {
						enabled = false;
						errorCounter.set(0);
						System.err.println("temporarily disabling Kinesis appender");

						new Thread() {
							public void run() {
								try {
									Thread.sleep(5*60*1000);
								} catch (InterruptedException e) {
									return;
								}

								System.err.println("re-enabling Kinesis appender");
								enabled = true;
							}
						}.start();
					}
				}
			}

			public @Override void onSuccess(PutRecordRequest request, PutRecordResult putRecordResult) {
				// nothing
			}
		});
	}
}