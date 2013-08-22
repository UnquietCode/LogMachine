package unquietcode.tools.logmachine.core;

import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineBuilder_because_debug_error_from_from$A_info_trace_warn;
import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineBuilder_because_debug_error_info_to_trace_warn;
import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineBuilder_because_from_from$A_to;
import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineBuilder_debug_error_from_from$A_info_to_trace_warn;
import unquietcode.tools.logmachine.builder.specific.SpecificLogMachine.SpecificLogMachineBuilder;
import unquietcode.tools.logmachine.core.topics.Topic;

/**
 * @author Ben Fagin
 * @version 2013-04-08
 */
public interface LogMachineBuilders<T> {
	void error(String message, Throwable exception);
	void error(String message, Object... data);

	void warn(String message, Throwable exception);
	void warn(String message, Object... data);

	void info(String message, Throwable exception);
	void info(String message, Object... data);

	void debug(String message, Throwable exception);
	void debug(String message, Object... data);

	void trace(String message, Throwable exception);
	void trace(String message, Object... data);

	SpecificLogMachineBuilder.$<Void> error();
	SpecificLogMachineBuilder.$<Void> warn();
	SpecificLogMachineBuilder.$<Void> info();
	SpecificLogMachineBuilder.$<Void> debug();
	SpecificLogMachineBuilder.$<Void> trace();

	GenericLogMachineBuilder_debug_error_from_from$A_info_to_trace_warn<Void> because(Throwable cause);
	GenericLogMachineBuilder_because_debug_error_info_to_trace_warn<Void> from(String location);
	GenericLogMachineBuilder_because_debug_error_info_to_trace_warn<Void> from();
	GenericLogMachineBuilder_because_debug_error_from_from$A_info_trace_warn<Void> to(Topic...topics);
	GenericLogMachineBuilder_because_from_from$A_to<Void> with(String key, String value);
	GenericLogMachineBuilder_because_from_from$A_to<Void> with(String key, Number value);
}
