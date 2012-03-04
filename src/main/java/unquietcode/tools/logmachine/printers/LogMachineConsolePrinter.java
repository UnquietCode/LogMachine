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
	public String printTitle() {
		return title;
	}

	@Override
	public String printHeader() {
		return "Printing log events to console window...";
	}

	@Override
	public String printFooter() {
		return "EOF";
	}

	//TODO threadz, stored in entry or writen by the current thread?

	@Override
	public void printEntry(Entry entry) {
		StringBuilder sb = new StringBuilder();
		
		// print time
		if (showTimestamps) {
			sb.append(entry.timestamp).append(" - ");
		}
		
		// print groups		
		if (!entry.groups.isEmpty()) {
			boolean first = true;
			sb.append("[ ");
			
			for (Enum group : entry.groups) {
				if (!first) {
					sb.append(" | ");
				} else {
					first = false;
				}
				
				sb.append(group);
			}
			
			sb.append(" ] ");
		}
		
		// print source
		if (entry.source != null) {
			sb.append(entry.source).append(" : ");
		}
		
		// print data
		sb.append(entry.message);
		
		// done
		System.out.println(sb.toString());
	}

	@Override
	public void showTimestamps(boolean value) {
		this.showTimestamps = value;
	}


}
