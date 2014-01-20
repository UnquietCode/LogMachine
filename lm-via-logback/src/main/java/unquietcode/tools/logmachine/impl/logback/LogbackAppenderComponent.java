package unquietcode.tools.logmachine.impl.logback;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import org.slf4j.impl.StaticLoggerBinder;
import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LoggingComponent;
import unquietcode.tools.logmachine.impl.slf4j.SLF4JHandler;

import java.util.Random;

/**
 * @author Ben Fagin
 * @version 2014-01-19
 */
public class LogbackAppenderComponent implements LoggingComponent {
	private static final Random random = new Random();
	private final SLF4JHandler handler = new SLF4JHandler();
	private final Logger logger;

	public LogbackAppenderComponent(Appender<ILoggingEvent> appender) {
		final String loggerName = "anonymous" + random.nextInt(Integer.MAX_VALUE);

		StaticLoggerBinder loggerBinder = StaticLoggerBinder.getSingleton();
		LoggerContext loggerContext = (LoggerContext) loggerBinder.getLoggerFactory();
		logger = loggerContext.getLogger(loggerName);
		logger.setAdditive(false);

		logger.addAppender(appender);
	}

	@Override
	public void handle(LogEvent event) {
		handler.logEvent(logger, event);
	}
}
