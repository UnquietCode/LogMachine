package unquietcode.tools.logmachine;

/**
* @author Benjamin Fagin
* @version 02-18-2012
*/
interface LogMachineBuilder {
	// severity
	void debug(String message, Object... data);
	void warn(String message, Object... data);
	void info(String message, Object... data);
	void error(String message, Object... data);
	void trace(String message, Object... data);

	// markers
	void mark(String event);
	void mark(String event, Enum... categories);

	// information
	//Level getLevel();

	
	interface LogMachine_from_because_to extends LogMachineBuilder {
		LogMachine_because_to from(String location);
		LogMachine_from_to because(Throwable throwable);
		LogMachine_from_because to(Enum... categories);
	}

	interface LogMachine_from_because extends LogMachineBuilder {
		LogMachine_because from(String location);
		LogMachine_from because(Throwable throwable);
	}

	interface LogMachine_from_to extends LogMachineBuilder {
		LogMachine_to from(String location);
		LogMachine_from to(Enum... categories);
	}

	interface LogMachine_because_to extends LogMachineBuilder {
		LogMachine_to because(Throwable throwable);
		LogMachine_because to(Enum... categories);
	}

	interface LogMachine_from extends LogMachineBuilder {
		LogMachine from(String location);
	}

	interface LogMachine_because extends LogMachineBuilder {
		LogMachine because(Throwable throwable);
	}

	interface LogMachine_to extends LogMachineBuilder {
		LogMachine to(Enum... categories);
	}
}
