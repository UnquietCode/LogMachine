package unquietcode.tools.logmachine.impl.log4j;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LoggingComponent;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ben Fagin
 * @version 2014-01-19
 */
public class Log4jAppenderComponent implements LoggingComponent {
	private final Log4jHandler handler = new Log4jHandler();
	private final Logger logger = Log4jLogMachine.newAnonymousLogger();

	public Log4jAppenderComponent(Appender appender) {
		logger.addAppender(checkNotNull(appender));
	}

	@Override
	public void handle(LogEvent event) {
		handler.logEvent(logger, event);
	}
}