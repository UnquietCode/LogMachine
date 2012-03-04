package unquietcode.tools.logmachine;

/**
 * @author Benjamin Fagin
 * @version 02-12-2012
 */
public interface LogMachinePrinter {
	String printTitle();
	String printHeader();
	String printFooter();
	void printEntry(Entry entry);
	void showTimestamps(boolean value);
}
