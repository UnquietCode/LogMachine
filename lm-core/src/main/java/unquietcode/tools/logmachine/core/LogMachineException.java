package unquietcode.tools.logmachine.core;

public class LogMachineException extends RuntimeException {

	public LogMachineException(String message) {
		super(message);
	}

	public LogMachineException(Throwable cause) {
		super(cause);
	}

	public LogMachineException(String message, Throwable cause) {
		super(message, cause);
	}
}