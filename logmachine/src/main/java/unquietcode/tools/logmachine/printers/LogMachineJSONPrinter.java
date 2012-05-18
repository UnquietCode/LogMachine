package unquietcode.tools.logmachine.printers;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ObjectNode;
import unquietcode.tools.logmachine.Entry;
import unquietcode.tools.logmachine.Level;
import unquietcode.tools.logmachine.LogMachinePrinter;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * @author Ben Fagin (Nokia)
 * @version 03-04-2012
 */
public class LogMachineJSONPrinter implements LogMachinePrinter {
	private ObjectWriter writer;
	private ObjectMapper mapper;
	private File outputFile;
	
	public LogMachineJSONPrinter(File outputFile) {
		if (outputFile == null) {
			throw new IllegalArgumentException("Output file resource cannot be null.");
		}

		this.outputFile = outputFile;
		
		mapper = new ObjectMapper();
		mapper.setVisibilityChecker(mapper.getVisibilityChecker().with(JsonAutoDetect.Visibility.NONE));
		writer = mapper.writer();
	}

	@Override
	public void printTitle() {
		// nothing
	}

	@Override
	public void printHeader() {
		// nothing
	}

	@Override
	public void printFooter() {
		// nothing
	}

	@Override
	public void printEntry(Entry entry) {
		String json;
		
		try {
			JSONEntry jsonEntry = new JSONEntry(entry);
			//StringWriter sWriter = new StringWriter();
			writer.writeValue(outputFile, jsonEntry);
			//json = sWriter.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		//System.out.println(json);
	}


	private class JSONEntry extends Entry {
		@JsonProperty final Level level = super.level;
		@JsonProperty final String message = super.message;
		@JsonProperty final String source = super.source;
		@JsonProperty final long ts = super.timestamp;
		@JsonProperty final List<Enum> groups = super.groups;
		@JsonProperty final String threadName = super.threadName;
		final Throwable cause = super.cause;

		@JsonProperty
		JsonNode getCause() {
			if (cause == null) {
				return null;
			}
			
			ObjectNode node = mapper.createObjectNode();
			node.put("_class", cause.getClass().getName());
			node.put("message", cause.getMessage());
			node.put("stackTrace", getStackTrace());
			
			return node;
		}

		// only call if cause is not null!
		private String getStackTrace() {
			StringWriter sWriter = new StringWriter();
			PrintWriter pWriter = new PrintWriter(sWriter);

			cause.printStackTrace(pWriter);
			String raw = sWriter.toString();
			
			// change the newlines into <br/>
			raw = raw.replaceAll("\n", "<br/>");
			
			// change the tabs into 4 &nbsp;
			raw = raw.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
			
			return raw;
		}
		
		
		JSONEntry(Level level, String message, String source, Throwable cause, List<Enum> groups) {
			super(level, message, source, cause, groups);
		}
		
		JSONEntry(Entry entry) {
			this(entry.level, entry.message, entry.source, entry.cause, entry.groups);
		}
	}
}
