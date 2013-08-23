package unquietcode.tools.logmachine.core;

import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineBuilder_2because_2debug_2error_2from_2from_1A_2info_2trace_2warn;
import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineBuilder_2because_2debug_2error_2info_2to_2trace_2warn;
import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineBuilder_2because_2from_2from_1A_2to;
import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineBuilder_2debug_2error_2from_2from_1A_2info_2to_2trace_2warn;
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

	SpecificLogMachineBuilder.Start<Void> error();
	SpecificLogMachineBuilder.Start<Void> warn();
	SpecificLogMachineBuilder.Start<Void> info();
	SpecificLogMachineBuilder.Start<Void> debug();
	SpecificLogMachineBuilder.Start<Void> trace();

	GenericLogMachineBuilder_2debug_2error_2from_2from_1A_2info_2to_2trace_2warn<Void> because(Throwable cause);
	GenericLogMachineBuilder_2because_2debug_2error_2info_2to_2trace_2warn<Void> from(String location);
	GenericLogMachineBuilder_2because_2debug_2error_2info_2to_2trace_2warn<Void> from();
	GenericLogMachineBuilder_2because_2debug_2error_2from_2from_1A_2info_2trace_2warn<Void> to(Topic...topics);
	GenericLogMachineBuilder_2because_2from_2from_1A_2to<Void> with(String key, String value);
	GenericLogMachineBuilder_2because_2from_2from_1A_2to<Void> with(String key, Number value);
}
