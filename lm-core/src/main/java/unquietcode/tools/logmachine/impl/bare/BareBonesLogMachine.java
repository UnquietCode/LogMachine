package unquietcode.tools.logmachine.impl.bare;

import unquietcode.tools.logmachine.core.Level;
import unquietcode.tools.logmachine.core.LogMachine;

/**
 * @author Ben Fagin
 * @version 2014-01-26
 */
public class BareBonesLogMachine extends LogMachine<BareBonesLogger> {
	private static final BareBonesHandler HANDLER = new BareBonesHandler();

	public BareBonesLogMachine(String name) {
		super(new BareBonesLogger(name), HANDLER);
	}

	public void setLevel(Level level) {
		getNativeLogger().setLevel(level);
	}
}
