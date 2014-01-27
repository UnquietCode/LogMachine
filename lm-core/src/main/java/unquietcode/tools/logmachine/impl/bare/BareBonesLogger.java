package unquietcode.tools.logmachine.impl.bare;

import unquietcode.tools.logmachine.core.Level;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ben Fagin
 * @version 2014-01-26
 */
public class BareBonesLogger {
	private final String name;
	private Level level;

	public BareBonesLogger(String name) {
		this(name, Level.TRACE);
	}

	public BareBonesLogger(String name, Level level) {
		this.name = checkNotNull(name);
		setLevel(level);
	}

	public String getName() {
		return name;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = checkNotNull(level);
	}
}
