package unquietcode.tools.logmachine.impl.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.core.topics.Topic;

/**
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class SLF4JLogMachine extends LogMachine<Logger> {
	private static final SLF4JHandler HANDLER = new SLF4JHandler();

	public SLF4JLogMachine(Logger log) {
		super(log, HANDLER);
	}

	public SLF4JLogMachine(Class clazz) {
		this(LoggerFactory.getLogger(clazz));
	}

	public SLF4JLogMachine(String name) {
		this(LoggerFactory.getLogger(name));
	}

	public SLF4JLogMachine(Topic topic) {
		this(topic, new Topic[0]);
	}

	public SLF4JLogMachine(Topic first, Topic...topics) {
		this(makeLoggerName(combine(first, topics)));
		subscribe(combine(first, topics));
	}
}
