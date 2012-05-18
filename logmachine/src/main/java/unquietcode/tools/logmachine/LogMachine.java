package unquietcode.tools.logmachine;


import unquietcode.tools.logmachine.builder.*;

import java.util.Arrays;

/**
 * @author Benjamin Fagin
 * @version 02-12-2012
 * @version 05-16-2012
 */
public class LogMachine implements LogMachineBuilder_because_from_to<Void> {
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
	}



	private String _format(String message, Object...data) {
		if (data.length == 0) {
			return message;
		}

		ArrayIterator<Object> it = new ArrayIterator<Object>(data);
		return message.replaceAll("\\{(\\s*)([0-9]*)(\\s*)\\}", "$1"+it.get()+"$3");

		// TODO change this to an actual Matcher/Pattern implementation
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

	public boolean isError() {
		return level == Level.ERROR || level.isGreaterThan(Level.ERROR);
	}

	public boolean isWarn() {
		return level == Level.WARN || level.isGreaterThan(Level.WARN);
	}

	public boolean isInfo() {
		return level == Level.INFO || level.isGreaterThan(Level.INFO);
	}

	public boolean isDebug() {
		return level == Level.DEBUG || level.isGreaterThan(Level.DEBUG);
	}

	public boolean isTrace() {
		return level == Level.TRACE || level.isGreaterThan(Level.TRACE);
	}

	//==o==o==o==o==o==o==| logging methods |==o==o==o==o==o==o==//

	/**
	 * Logs a message to the printer. At this point the data has been approved, filtered, etc.
	 * It doesn't take a level, or a piece, it simply
	 *
	 * @param message to log, should not be null, depends on the printer
	 * @param source  to log, could be null
	 * @param cause   of the message, could be null
	 * @param categories to log, could be
	 */
	private void _log(Level level, String message, String source, Throwable cause, Enum[] categories) {
		Entry entry = new Entry(level, message, source, cause, Arrays.asList(categories));
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

		_log(level, message, source, cause, categories);
	}

	void  _mark(String event, Enum[] categories) {
		throw new UnsupportedOperationException("not implemented");
	}

	void _mark(String event) {
		throw new UnsupportedOperationException("not implemented");
	}

	//==o==o==o==o==o==o==| builder methods |==o==o==o==o==o==o==//

	public Void debug(String message, Object... data) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).debug(message, data);
	}

	public Void error(String message, Object... data) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).error(message, data);
	}

	public Void info(String message, Object... data) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).info(message, data);
	}

	public Void trace(String message, Object... data) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).trace(message, data);
	}

	public Void warn(String message, Object... data) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).warn(message, data);
	}

	public Void mark(String event) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).mark(event);
	}

	public Void mark(String event, Enum... categories) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).mark(event, categories);
	}

	public LogMachineBuilder_from_to<Void> because(Throwable cause) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).because(cause);
	}

	public LogMachineBuilder_because_to<Void> from(String location) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).from(location);
	}

	public LogMachineBuilder_because_from<Void> to(Enum... categories) {
		return LogMachineGenerator.start(new LogMachineHelperImpl(this)).to(categories);
	}
}

