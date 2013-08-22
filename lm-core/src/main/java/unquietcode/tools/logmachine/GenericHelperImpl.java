package unquietcode.tools.logmachine;

import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineHelper;
import unquietcode.tools.logmachine.core.BaseLogMachine;
import unquietcode.tools.logmachine.core.Level;


public class GenericHelperImpl extends BuilderHelperBase implements GenericLogMachineHelper {

	public GenericHelperImpl(BaseLogMachine lm) {
		super(lm);
	}

	@Override
	public void error(String message, Object...data) {
		logEvent(Level.ERROR, message, data);
	}

	@Override
	public void warn(String message, Object...data) {
		logEvent(Level.WARN, message, data);
	}

	@Override
	public void info(String message, Object...data) {
		logEvent(Level.INFO, message, data);
	}

	@Override
	public void debug(String message, Object...data) {
		logEvent(Level.DEBUG, message, data);
	}

	@Override
	public void trace(String message, Object...data) {
		logEvent(Level.TRACE, message, data);
	}

	private void logEvent(Level level, String message, Object[] data) {
		event.setLevel(level);
		event.setMessage(message);
		event.setReplacements(data);
		lm._log(event);
	}
}