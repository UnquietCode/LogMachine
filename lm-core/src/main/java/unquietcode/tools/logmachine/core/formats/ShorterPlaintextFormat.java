package unquietcode.tools.logmachine.core.formats;

import unquietcode.tools.logmachine.core.LogEvent;

/**
 * Warning: this class may be useless.
 *
 * @author Ben Fagin
 * @version 10-24-2012
 */
public class ShorterPlaintextFormat implements Format {

	@Override
	public String format(LogEvent event) {
		StringBuilder sb = new StringBuilder();

		// print groups
		if (!event.getGroups().isEmpty()) {
			boolean first = true;
			sb.append("[");

			for (Enum group : event.getGroups()) {
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
		if (event.getLocation() != null) {
			sb.append("(").append(event.getLevel()).append(") ");
		}

		// print data
		sb.append(event.getFormattedMessage());

		return sb.toString();
	}
}
