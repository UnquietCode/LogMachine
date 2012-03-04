package unquietcode.tools.logmachine.printers;

import org.slf4j.Logger;
import unquietcode.tools.logmachine.Entry;
import unquietcode.tools.logmachine.LogMachinePrinter;

/**
 * @author Benjamin Fagin
 * @version 02-18-2012
 * 
 * An implementation of {@link LogMachinePrinter} which writes its events to a
 * SLF4J {@link Logger} log. The source and group information is combined with the
 * log message, and the throwable passed separately.
 *
 * You must have an SLF4J implementation available at runtime in order to see
 * and log statements output to your logs.
 */
public class LogMachineSLF4JPrinter implements LogMachinePrinter {
	private final Logger logger;
	
	public LogMachineSLF4JPrinter(Logger logger) {
		if (logger == null) {
			throw new IllegalArgumentException("logger cannot be null");
		}
		
		this.logger = logger;
	}
		
	@Override
	public void printTitle() {
		// nothing
	}

	@Override
	public void printHeader() {
		// nothing
	}

	@Override
	public void printFooter() {
		// nothing
	}

	@Override
	public void printEntry(Entry entry) {
		switch (entry.level) {
			case ERROR: logError(entry);    break;
			case WARN:  logWarn(entry);     break;
			case INFO:  logInfo(entry);     break;
			case DEBUG: logDebug(entry);    break;
			case TRACE: logTrace(entry);    break;
			default: throw new RuntimeException("Unrecognized level: "+entry.level+" (this is an internal error).");
		}
	}
	
	private void logError(Entry e) {
		if (logger.isErrorEnabled()) {
			if (e.cause != null) {
				logger.error(makeMessage(e), e.cause);
			} else {
				logger.error(makeMessage(e));
			}
		}
	}
	
	private void logWarn(Entry e) {
		if (logger.isWarnEnabled()) {
			if (e.cause != null) {
				logger.warn(makeMessage(e), e.cause);
			} else {
				logger.warn(makeMessage(e));
			}			
		}
	}
	
	private void logInfo(Entry e) {
		if (logger.isInfoEnabled()) {
			if (e.cause != null) {
				logger.info(makeMessage(e), e.cause);
			} else {
				logger.info(makeMessage(e));
			}			
		}
	}
	
	private void logDebug(Entry e) {
		if (logger.isDebugEnabled()) {
			if (e.cause != null) {
				logger.debug(makeMessage(e), e.cause);
			} else {
				logger.debug(makeMessage(e));
			}			
		}
	}
	
	private void logTrace(Entry e) {
		if (logger.isTraceEnabled()) {
			if (e.cause != null) {
				logger.trace(makeMessage(e), e.cause);
			} else {
				logger.trace(makeMessage(e));
			}			
		}
	}
	
	private String makeMessage(Entry entry) {
		StringBuilder sb = new StringBuilder();

		// print groups		
		if (!entry.groups.isEmpty()) {
			boolean first = true;
			sb.append("[");

			for (Enum group : entry.groups) {
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
		if (entry.source != null) {
			sb.append("@:").append(entry.source).append(" - ");
		}

		// print data
		sb.append(entry.message);
		
		return sb.toString();
	}
}
