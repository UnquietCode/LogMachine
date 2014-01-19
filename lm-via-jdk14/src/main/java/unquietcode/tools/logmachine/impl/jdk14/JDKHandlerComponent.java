package unquietcode.tools.logmachine.impl.jdk14;

import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.LoggingComponent;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ben Fagin
 * @version 2014-01-17
 */
public class JDKHandlerComponent implements LoggingComponent {
	private final JDKHandler handler = new JDKHandler();
	private final Logger logger;

	public JDKHandlerComponent(Handler handler) {
		logger = Logger.getAnonymousLogger();
		logger.setUseParentHandlers(false);
		logger.addHandler(checkNotNull(handler));
		logger.setLevel(Level.ALL);
	}

	@Override
	public void handle(LogEvent event) {
		handler.logEvent(logger, event);
	}
}