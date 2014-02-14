package unquietcode.tools.logmachine.impl.kinesis;

import com.amazonaws.services.kinesis.clientlibrary.exceptions.InvalidStateException;
import com.amazonaws.services.kinesis.clientlibrary.exceptions.ShutdownException;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorCheckpointer;
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownReason;
import com.amazonaws.services.kinesis.model.Record;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LoggingComponent;
import unquietcode.tools.logmachine.core.formats.JSONFormatter;
import unquietcode.tools.logmachine.core.formats.Unformatter;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author Ben Fagin
 * @version 2014-01-31
 */
public abstract class KinesisConsumer implements LoggingComponent, IRecordProcessor {
//	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
//	private final long startTime;
	private Unformatter unformatter = new JSONFormatter();

//	public KinesisConsumer(String streamName, long startTime) {
//		this.streamName = streamName;
//		this.startTime = startTime;
//	}

	/*
		then build an kinesis-to-es plugin which
		loads the kinesis application as a main loader
		and then you have it
	 */

	@Override
	public void initialize(String shardID) {
		// nothing
	}

	@Override
	public void processRecords(List<Record> records, IRecordProcessorCheckpointer checkpointer) {
		for (Record record : records) {
			byte[] _data = record.getData().array();
			String data = new String(_data, Charset.forName("UTF-8"));

			final LogEvent event = unformatter.unformat(data);

			try {
				handle(event);
			} catch (Exception ex) {
				ex.printStackTrace();
				// TODO  now what?
			}
		}

		try {
			checkpointer.checkpoint();
		} catch (InvalidStateException e) {
			throw new RuntimeException("error while committing state to kinesis", e);
		} catch (ShutdownException e) {
			// don't care
		}
	}

	@Override
	public void shutdown(IRecordProcessorCheckpointer checkpointer, ShutdownReason reason) {
		// nothing
	}

/*
	public void x() {
		GetShardIteratorRequest iteratorRequest = new GetShardIteratorRequest()
			.withStreamName(streamName)
			.withShardIteratorType(ShardIteratorType.AT_SEQUENCE_NUMBER)
			.withStartingSequenceNumber(""+startTime)
			.withShardId(dateFormat.format(new Date(startTime)))
		;

		String shardIterator = kinesis.getShardIterator(iteratorRequest).getShardIterator();

		GetRecordsRequest request = new GetRecordsRequest()
			.withShardIterator(shardIterator)
			.withLimit(batchSize)
		;

		kinesis.getRecordsAsync(request, new AsyncHandler<GetRecordsRequest, GetRecordsResult>() {
			public @Override void onSuccess(GetRecordsRequest request, GetRecordsResult result) {

				for (Record record : result.getRecords()) {
					byte[] _data = record.getData().array();
					String data = new String(_data, Charset.forName("UTF-8"));

					// move the json-to-event code somewhere, so that a formatter can unformat
				}
			}

			@Override
			public void onError(Exception exception) {
				// nothing
			}

		});

	}
*/

}
