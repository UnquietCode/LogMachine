package unquietcode.tools.logmachine;

import unquietcode.tools.flapi.support.v0_2.ObjectWrapper;
import unquietcode.tools.logmachine.builder.GenericHelper;
import unquietcode.tools.logmachine.builder.LogMachineHelper;
import unquietcode.tools.logmachine.builder.SpecificHelper;


public class LogMachineHelperImpl implements LogMachineHelper {
	private final LogMachine lm;
	private final Level level;

	public LogMachineHelperImpl(LogMachine lm) {
		this.lm = lm;
		this.level = null;
	}

	public LogMachineHelperImpl(LogMachine lm, Level level) {
		this.lm = lm;
		this.level = level;
	}


	@Override
	public void specific(ObjectWrapper<SpecificHelper> _helper1) {
		_helper1.set(new SpecificHelperImpl(lm, level));
	}

	@Override
	public void generic(ObjectWrapper<GenericHelper> _helper1) {
		_helper1.set(new GenericHelperImpl(lm));
	}
}