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

		// log level
		sb.append("[").append(event.getLevel()).append("] ");

		// topics
		if (!event.getTopics().isEmpty()) {
			boolean first = true;
			sb.append("[");

			for (Topic topic : event.getTopics()) {
				if (!first) {
					sb.append(" | ");
				} else {
					first = false;
				}

				sb.append(topic.name());
			}

			sb.append("] ");
		}

		// print source
		if (event.getLocation() != null) {
			sb.append("(").append(event.getLocation()).append(") ");
		}

		// print data
		sb.append("- ").append(event.getFormattedMessage());

		return sb;
	}
}
