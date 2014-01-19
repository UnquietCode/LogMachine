package unquietcode.tools.logmachine.impl.sqs;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.amazonaws.services.sqs.buffered.AmazonSQSBufferedAsyncClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LoggingComponent;
import unquietcode.tools.logmachine.core.formats.Formatter;
import unquietcode.tools.logmachine.core.formats.JSONFormatter;

import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ben Fagin
 * @version 2014-01-13
 */
public class SQSAppender implements LoggingComponent {
	private static final int ERROR_THRESHOLD = 100;

	private final AmazonSQSAsync sqs;
	private final String queueURL;
	private Formatter formatter = new JSONFormatter();
	private boolean enabled = true;
	private final AtomicInteger errorCounter = new AtomicInteger(0);


	public SQSAppender(AWSCredentials credentials, String queueURL) {
		this(new StaticCredentialsProvider(credentials), queueURL);
	}

	public SQSAppender(AWSCredentialsProvider provider, String queueURL) {
		checkArgument(queueURL != null && !queueURL.trim().isEmpty());
		this.queueURL = queueURL;

		AmazonSQSAsync client = new AmazonSQSAsyncClient(provider);
		this.sqs = new AmazonSQSBufferedAsyncClient(client);
	}

	public void setFormatter(Formatter formatter) {
		this.formatter = checkNotNull(formatter);
	}

	@Override
	public void handle(LogEvent event) {
		if (!enabled) { return; }

		StringBuilder data = formatter.format(event);
		SendMessageRequest request = new SendMessageRequest(queueURL, data.toString());

		sqs.sendMessageAsync(request, new AsyncHandler<SendMessageRequest, SendMessageResult>() {
			public @Override void onError(Exception exception) {
				System.err.println("encountered an error while writing log data to SQS");
				exception.printStackTrace();

				if (errorCounter.incrementAndGet() < ERROR_THRESHOLD) {
					return;
				}

				// disable ourselves for 5 minutes after crossing the error threshold

				synchronized (errorCounter) {
					if (errorCounter.incrementAndGet() >= ERROR_THRESHOLD) {
						enabled = false;
						errorCounter.set(0);
						System.err.println("temporarily disabling SQS appender");

						new Thread() {
							public void run() {
								try {
									Thread.sleep(5*60*1000);
								} catch (InterruptedException e) {
									return;
								}

								System.err.println("re-enabling SQS appender");
								enabled = true;
							}
						}.start();
					}
				}
			}

			public @Override void onSuccess(SendMessageRequest request, SendMessageResult sendMessageResult) {
				// nothing
			}
		});
	}
}