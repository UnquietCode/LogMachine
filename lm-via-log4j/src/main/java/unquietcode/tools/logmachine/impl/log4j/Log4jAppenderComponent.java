package unquietcode.tools.logmachine.impl.log4j;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LoggingComponent;

import java.util.Random;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ben Fagin
 * @version 2014-01-19
 */
public class Log4jAppenderComponent implements LoggingComponent {
	private static final Random random = new Random();
	private final Log4jHandler handler = new Log4jHandler();
	private final Logger logger;

	public Log4jAppenderComponent(Appender appender) {
		String loggerName = "anonymous" + random.nextInt(Integer.MAX_VALUE);
		this.logger = Logger.getLogger(loggerName);

		logger.addAppender(checkNotNull(appender));
		logger.setAdditivity(false);
		logger.setLevel(Level.ALL);
	}

	@Override
	public void handle(LogEvent event) {
		handler.logEvent(logger, event);
	}
}