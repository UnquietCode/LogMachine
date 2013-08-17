package unquietcode.tools.logmachine.core;

import unquietcode.tools.logmachine.builder.Generic.GenericBuilder_because_debug_error_from_from$A_info_trace_warn;
import unquietcode.tools.logmachine.builder.Generic.GenericBuilder_because_debug_error_info_to_trace_warn;
import unquietcode.tools.logmachine.builder.Generic.GenericBuilder_because_from_from$A_to;
import unquietcode.tools.logmachine.builder.Generic.GenericBuilder_debug_error_from_from$A_info_to_trace_warn;
import unquietcode.tools.logmachine.builder.Specific.SpecificBuilder_because_from_from$A_to;

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

	SpecificBuilder_because_from_from$A_to<Void> error();
	SpecificBuilder_because_from_from$A_to<Void> warn();
	SpecificBuilder_because_from_from$A_to<Void> info();
	SpecificBuilder_because_from_from$A_to<Void> debug();
	SpecificBuilder_because_from_from$A_to<Void> trace();

	GenericBuilder_debug_error_from_from$A_info_to_trace_warn<Void> because(Throwable cause);
	GenericBuilder_because_debug_error_info_to_trace_warn<Void> from(String location);
	GenericBuilder_because_debug_error_info_to_trace_warn<Void> from();
	GenericBuilder_because_debug_error_from_from$A_info_trace_warn<Void> to(Enum... topics);
	GenericBuilder_because_from_from$A_to<Void> with(String key, String value);
	GenericBuilder_because_from_from$A_to<Void> with(String key, Number value);
}
