package unquietcode.tools.logmachine;

import unquietcode.tools.logmachine.builder.Generic.GenericHelper;
import unquietcode.tools.logmachine.builder.LogMachine.LogMachineHelper;
import unquietcode.tools.logmachine.builder.Specific.SpecificHelper;
import unquietcode.tools.logmachine.core.BaseLogMachine;
import unquietcode.tools.logmachine.core.Level;

import java.util.concurrent.atomic.AtomicReference;


public class LogMachineHelperImpl implements LogMachineHelper {
	private final BaseLogMachine lm;
	private final Level level;

	public LogMachineHelperImpl(BaseLogMachine lm) {
		this.lm = lm;
		this.level = null;
	}

	public LogMachineHelperImpl(BaseLogMachine lm, Level level) {
		this.lm = lm;
		this.level = level;
	}


	@Override
	public void specific(AtomicReference<SpecificHelper> _helper1) {
		_helper1.set(new SpecificHelperImpl(lm, level));
	}

	@Override
	public void generic(AtomicReference<GenericHelper> _helper1) {
		_helper1.set(new GenericHelperImpl(lm));
	}
}