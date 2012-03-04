package unquietcode.tools.logmachine;



import unquietcode.tools.logmachine.builder.*;

import java.util.Arrays;

/**
 * @author Benjamin Fagin
 * @version 02-12-2012
 */
public class LogMachine {
	
	Level level;
	LogMachinePrinter printer;

	LogMachine(Level startingLevel, LogMachinePrinter printer) {
		if (printer == null) {
			throw new IllegalArgumentException("Printer cannot be null.");
		}
		
		if (startingLevel == null) {
			throw new IllegalArgumentException("Starting level cannot be null.");
		}
		
		this.level = startingLevel;
		this.printer = printer;
		printer.printHeader();
	}

	/**
	 * Logs a message to the printer. At this point the data has been approved, filtered, etc.
	 * It doesn't take a level, or a piece, it simply
	 *
	 * @param message to log, should not be null, depends on the printer
	 * @param source  to log, could be null
	 * @param cause   of the message, could be null
	 * @param categories to log, could be 
	 */
	private void _log(String message, String source, Throwable cause, Enum[] categories) {
		Entry entry = new Entry(message, source, cause, Arrays.asList(categories));
		printer.printEntry(entry);
	}

	void _log(Level level, String message, Object[] data, String source, Throwable cause, Enum[] categories) {
		// skip altogether if this should not be logged
		if (this.level != level && this.level.isGreaterThan(level)) {
			return;
		}
		
		if (categories == null) {
			categories = new Enum[]{};
		}
		
		if (data == null) {
			data = new Object[]{ null };
		}
		
		if (message == null) {
			message = "";
		} else {
			message = _format(message, data);
		}

		_log(message, source, cause, categories);
	}
	
	void  _mark(String event, Enum[] categories) {
		throw new UnsupportedOperationException("not implemented");
	}

	void _mark(String event) {
		throw new UnsupportedOperationException("not implemented");
	}

	private String _format(String message, Object...data) {
		if (data.length == 0) {
			return message;
		}

		ArrayIterator<Object> it = new ArrayIterator<Object>(data);
		return message.replaceAll("\\{(\\s*)([0-9]*)(\\s*)\\}", "$1"+it.get()+"$2");
	}

	class ArrayIterator<T> {
		int count = 0;
		T[] array;
		
		ArrayIterator(T[] array) {
			this.array = array;
		}
		
		public T get() {
			return array[count++];
		}
		
	}

	enum OrderBy {
		MESSAGE_ASCENDING, MESSAGE_DESCENDING,
		TIMESTAMP_ASCENDING, TIMESTAMP_DESCENDING,
		URI_ASCENDING, URI_DESCENDING,
	}
	
	//---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---o---//

	private ImplLogMachineBuilder_because_from_to getBuilder() {
		LogMachineHelperImpl helper = new LogMachineHelperImpl(this);
		ImplLogMachineBuilder_because_from_to builder = new ImplLogMachineBuilder_because_from_to(helper);
		return builder;
	}
	

	public ImplLogMachineBuilder_because_to from(String location) {
		ImplLogMachineBuilder_because_from_to builder = getBuilder();
		return builder.from(location);
	}

	public ImplLogMachineBuilder_because_from to(Enum... categories) {
		ImplLogMachineBuilder_because_from_to builder = getBuilder();
		return builder.to(categories);
	}

	public ImplLogMachineBuilder_from_to because(Throwable cause) {
		ImplLogMachineBuilder_because_from_to builder = getBuilder();
		return builder.because(cause);
	}

	public void debug(String message, Object... data) {
		ImplLogMachineBuilder_because_from_to builder = getBuilder();
		builder.debug(message, data);
	}

	public void warn(String message, Object... data) {
		ImplLogMachineBuilder_because_from_to builder = getBuilder();
	    builder.warn(message, data);
	}

	public void info(String message, Object... data) {
		ImplLogMachineBuilder_because_from_to builder = getBuilder();
		builder.info(message, data);
	}

	public void error(String message, Object... data) {
		ImplLogMachineBuilder_because_from_to builder = getBuilder();
		builder.error(message, data);
	}

	public void trace(String message, Object... data) {
		ImplLogMachineBuilder_because_from_to builder = getBuilder();
		builder.trace(message, data);
	}

	public void mark(String event) {
		ImplLogMachineBuilder_because_from_to builder = getBuilder();
		builder.mark(event);
	}

	public void mark(String event, Enum... categories) {
		ImplLogMachineBuilder_because_from_to builder = getBuilder();
		builder.mark(event, categories);
	}
}

