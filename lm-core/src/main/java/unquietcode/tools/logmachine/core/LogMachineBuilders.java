package unquietcode.tools.logmachine.core;

import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineBuilder_2because_4f_2debug_4f_2error_4f_2from_4f_2fromHere_4f_2info_4f_2trace_4f_2warn_4f_2with_4f_2with_1A_4f;
import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineBuilder_2because_4f_2debug_4f_2error_4f_2info_4f_2to_4f_2trace_4f_2warn_4f_2with_4f_2with_1A_4f;
import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineBuilder_2because_4f_2from_4f_2fromHere_4f_2to_4f_2with_4f_2with_1A_4f;
import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineBuilder_2debug_4f_2error_4f_2from_4f_2fromHere_4f_2info_4f_2to_4f_2trace_4f_2warn_4f_2with_4f_2with_1A_4f;
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

	SpecificLogMachineBuilder.Start error();
	SpecificLogMachineBuilder.Start warn();
	SpecificLogMachineBuilder.Start info();
	SpecificLogMachineBuilder.Start debug();
	SpecificLogMachineBuilder.Start trace();

	T getNativeLogger();

	GenericLogMachineBuilder_2debug_4f_2error_4f_2from_4f_2fromHere_4f_2info_4f_2to_4f_2trace_4f_2warn_4f_2with_4f_2with_1A_4f<Void> because(Throwable cause);
	GenericLogMachineBuilder_2because_4f_2debug_4f_2error_4f_2info_4f_2to_4f_2trace_4f_2warn_4f_2with_4f_2with_1A_4f<Void> from(String location);
	GenericLogMachineBuilder_2because_4f_2debug_4f_2error_4f_2info_4f_2to_4f_2trace_4f_2warn_4f_2with_4f_2with_1A_4f<Void> fromHere();
	GenericLogMachineBuilder_2because_4f_2debug_4f_2error_4f_2from_4f_2fromHere_4f_2info_4f_2trace_4f_2warn_4f_2with_4f_2with_1A_4f<Void> to(Topic... topics);
	GenericLogMachineBuilder_2because_4f_2from_4f_2fromHere_4f_2to_4f_2with_4f_2with_1A_4f<Void> with(String key, String value);
	GenericLogMachineBuilder_2because_4f_2from_4f_2fromHere_4f_2to_4f_2with_4f_2with_1A_4f<Void> with(String key, Number value);
}
