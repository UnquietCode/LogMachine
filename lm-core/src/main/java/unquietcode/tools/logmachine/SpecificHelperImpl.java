package unquietcode.tools.logmachine;

import unquietcode.tools.logmachine.builder.SpecificHelper;
import unquietcode.tools.logmachine.core.BaseLogMachine;
import unquietcode.tools.logmachine.core.Level;


public class SpecificHelperImpl extends BuilderHelperBase implements SpecificHelper {

	public SpecificHelperImpl(BaseLogMachine lm, Level level) {
		super(lm);
		event.setLevel(level);
	}

	@Override
	public void send(String message, Object... data) {
		event.setMessage(message);
		event.setReplacements(data);

		lm._log(event);
	}
}