package unquietcode.tools.logmachine;

/**
 * @author Benjamin Fagin
 * @version 02-12-2012
 */
public interface LogMachinePrinter {
	void printTitle();
	void printHeader();
	void printFooter();
	void printEntry(Entry entry);
	void showTimestamps(boolean value);
}
