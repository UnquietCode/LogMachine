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

	/**
	 * Log a new error event.
	 *
	 * @param message message
	 * @param exception an exception to log with the event
	 */
	void error(String message, Throwable exception);

	/**
	 * Log a new error event.
	 * The last argument of the data can be set
	 * to an exception, as a shortcut for 'because(..)'.
	 *
	 * @param message message
	 * @param data replacements, with or without an exception
	 */
	void error(String message, Object...data);

	/**
	 * Log a new warn level event.
	 *
	 * @param message message
	 * @param exception an exception to log with the event
	 */
	void warn(String message, Throwable exception);

	/**
	 * Log a new warn level event.
	 * The last argument of the data can be set
	 * to an exception, as a shortcut for 'because(..)'.
	 *
	 * @param message message
	 * @param data replacements, with or without an exception
	 */
	void warn(String message, Object...data);

	/**
	 * Log a new info level event.
	 *
	 * @param message message
	 * @param exception an exception to log with the event
	 */
	void info(String message, Throwable exception);

	/**
	 * Log a new info level event.
	 * The last argument of the data can be set
	 * to an exception, as a shortcut for 'because(..)'.
	 *
	 * @param message message
	 * @param data replacements, with or without an exception
	 */
	void info(String message, Object...data);

	/**
	 * Log a new debug level event.
	 *
	 * @param message message
	 * @param exception an exception to log with the event
	 */
	void debug(String message, Throwable exception);

	/**
	 * Log a new debug level event.
	 * The last argument of the data can be set
	 * to an exception, as a shortcut for 'because(..)'.
	 *
	 * @param message message
	 * @param data replacements, with or without an exception
	 */
	void debug(String message, Object...data);

	/**
	 * Log a new trace level event.
	 *
	 * @param message message
	 * @param exception an exception to log with the event
	 */
	void trace(String message, Throwable exception);

	/**
	 * Log a new trace level event.
	 * The last argument of the data can be set
	 * to an exception, as a shortcut for 'because(..)'.
	 *
	 * @param message message
	 * @param data replacements, with or without an exception
	 */
	void trace(String message, Object...data);

	/**
	 * Begin logging a new error level event.
	 *
	 * This call and all chained calls will effectively be
	 * no-op if the underlying logger is not accepting
	 * events at this level.
	 */
	SpecificLogMachineBuilder.Start error();

	/**
	 * Begin logging a new warn level event.
	 *
	 * This call and all chained calls will effectively be
	 * no-op if the underlying logger is not accepting
	 * events at this level.
	 */
	SpecificLogMachineBuilder.Start warn();

	/**
	 * Begin logging a new info level event.
	 *
	 * This call and all chained calls will effectively be
	 * no-op if the underlying logger is not accepting
	 * events at this level.
	 */
	SpecificLogMachineBuilder.Start info();

	/**
	 * Begin logging a new debug level event.
	 *
	 * This call and all chained calls will effectively be
	 * no-op if the underlying logger is not accepting
	 * events at this level.
	 */
	SpecificLogMachineBuilder.Start debug();

	/**
	 * Begin logging a new trace level event.
	 *
	 * This call and all chained calls will effectively be
	 * no-op if the underlying logger is not accepting
	 * events at this level.
	 */
	SpecificLogMachineBuilder.Start trace();

	/**
	 * @return the underlying native logger
	 */
	T getNativeLogger();

	GenericLogMachineBuilder_2debug_4f_2error_4f_2from_4f_2fromHere_4f_2info_4f_2to_4f_2trace_4f_2warn_4f_2with_4f_2with_1A_4f<Void> because(Throwable cause);
	GenericLogMachineBuilder_2because_4f_2debug_4f_2error_4f_2info_4f_2to_4f_2trace_4f_2warn_4f_2with_4f_2with_1A_4f<Void> from(String location);
	GenericLogMachineBuilder_2because_4f_2debug_4f_2error_4f_2info_4f_2to_4f_2trace_4f_2warn_4f_2with_4f_2with_1A_4f<Void> fromHere();
	GenericLogMachineBuilder_2because_4f_2debug_4f_2error_4f_2from_4f_2fromHere_4f_2info_4f_2trace_4f_2warn_4f_2with_4f_2with_1A_4f<Void> to(Topic... topics);
	GenericLogMachineBuilder_2because_4f_2from_4f_2fromHere_4f_2to_4f_2with_4f_2with_1A_4f<Void> with(String key, String value);
	GenericLogMachineBuilder_2because_4f_2from_4f_2fromHere_4f_2to_4f_2with_4f_2with_1A_4f<Void> with(String key, Number value);
}
