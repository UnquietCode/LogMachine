package unquietcode.tools.logmachine.core.topics;

import unquietcode.tools.logmachine.impl.bare.BareBonesLogMachine;

import java.util.Arrays;

/**
 * This log machine uses topics to define itself.
 * All of the provided topics are
 *
 * @author Ben Fagin
 * @version 2014-01-26
 */
public class TopicLogMachine extends BareBonesLogMachine {

	// we never want to allow zero topics, so don't
	// use a plain old varargs, split it into (T,T*)

	public TopicLogMachine(Topic first, Topic...topics) {
		super(makeLoggerName(combine(first, topics)));
		setDefaultTopics(combine(first, topics));
	}

	protected static String makeLoggerName(Topic[] topics) {
		StringBuilder sb = new StringBuilder();

		for (Topic topic : topics) {
			sb.append(topic.name()).append("_");
		} sb.deleteCharAt(sb.length()-1);

		return sb.toString();
	}

	protected static <T> T[] combine(T first, T[] rest) {
		T[] retval = Arrays.copyOf(rest, rest.length + 1);
		retval[retval.length-1] = first;
		return retval;
	}
}