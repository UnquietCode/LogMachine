package unquietcode.tools.logmachine.printers;

import unquietcode.tools.logmachine.Entry;
import unquietcode.tools.logmachine.LogMachinePrinter;

/**
 * @author Benjamin Fagin
 * @version 02-18-2012
 */
public class LogMachineConsolePrinter implements LogMachinePrinter {
	String title;
	boolean showTimestamps = true;
	
	@Override
	public void printTitle() {
		// TODO
	}

	@Override
	public void printHeader() {
		System.out.println("Printing log events to console window...\n\n");
	}

	@Override
	public void printFooter() {
		System.out.println("\n\nClosing console log...\n");
	}

	@Override
	public void printEntry(Entry entry) {
		StringBuilder sb = new StringBuilder();

		// print time
		if (showTimestamps) {
			sb.append(entry.timestamp).append(" ");
		}

		// print severity
		sb.append(entry.level.name()).append("\t");
		
		// print thread name
		sb.append(entry.threadName).append("\t");
		
		// print groups		
		if (!entry.groups.isEmpty()) {
			boolean first = true;
			sb.append("[");
			
			for (Enum group : entry.groups) {
				if (!first) {
					sb.append(" | ");
				} else {
					first = false;
				}
				
				sb.append(group);
			}
			
			sb.append("] ");
		}
		
		// print source
		if (entry.source != null) {
			sb.append("(").append(entry.source).append(") ");
		}
		
		// print data
		sb.append(entry.message);

		// done
		System.out.println(sb.toString());

		// stack trace
		if (entry.cause != null) {
			entry.cause.printStackTrace(System.out);
		}
	}

	public void showTimestamps(boolean value) {
		this.showTimestamps = value;
	}


}
