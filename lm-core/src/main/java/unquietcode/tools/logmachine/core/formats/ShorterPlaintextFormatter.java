package unquietcode.tools.logmachine.core.formats;

import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.topics.Topic;

/**
 * Warning: this class may be useless.
 *
 * @author Ben Fagin
 * @version 10-24-2012
 */
public class ShorterPlaintextFormatter implements Formatter {

	@Override
	public StringBuilder format(LogEvent event) {
		StringBuilder sb = new StringBuilder();

		// thread
		sb.append("[").append(event.getThreadName()).append("]");

		// log level
		sb.append(" ").append(event.getLevel());

		// print source
		if (event.getLocation() != null) {
			sb.append(" ").append(event.getLocation());
		}

		// topics
		if (!event.getTopics().isEmpty()) {
			boolean first = true;

			if (event.getLocation() != null) {
				sb.append(" -->");
			}

			sb.append(" [");

			for (Topic topic : event.getTopics()) {
				if (!first) {
					sb.append(" | ");
				} else {
					first = false;
				}

				sb.append(topic.name());
			}

			sb.append("]");
		}

		// print data
		sb.append("\n").append(event.getFormattedMessage());

		return sb;
	}
}
