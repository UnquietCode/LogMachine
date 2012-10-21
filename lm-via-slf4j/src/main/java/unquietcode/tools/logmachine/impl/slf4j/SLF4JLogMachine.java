package unquietcode.tools.logmachine.impl.slf4j;

import org.slf4j.Logger;
import unquietcode.tools.logmachine.LogEventHandler;
import unquietcode.tools.logmachine.LogMachine;

/**
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class SLF4JLogMachine extends LogMachine<Logger> {
	private final Logger log;
	private static final LogEventHandler<Logger> HANDLER = new SLF4JHandler();


	public SLF4JLogMachine(Logger log) {
		super(log, HANDLER);
		this.log = log;
	}

	@Override
	public boolean isError() {
		return log.isErrorEnabled();
	}

	@Override
	public boolean isWarn() {
		return log.isWarnEnabled();
	}

	@Override
	public boolean isInfo() {
		return log.isInfoEnabled();
	}

	@Override
	public boolean isDebug() {
		return log.isDebugEnabled();
	}

	@Override
	public boolean isTrace() {
		return log.isTraceEnabled();
	}
}
