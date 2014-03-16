package unquietcode.tools.logmachine.core;


import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineBuilder_2because_4f_2debug_4f_2error_4f_2from_4f_2info_4f_2trace_4f_2warn_4f_2with_4f_2with_1A_4f;
import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineBuilder_2because_4f_2debug_4f_2error_4f_2info_4f_2to_4f_2trace_4f_2warn_4f_2with_4f_2with_1A_4f;
import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineBuilder_2because_4f_2from_4f_2to_4f_2with_4f_2with_1A_4f;
import unquietcode.tools.logmachine.builder.generic.GenericLogMachine.GenericLogMachineBuilder_2debug_4f_2error_4f_2from_4f_2info_4f_2to_4f_2trace_4f_2warn_4f_2with_4f_2with_1A_4f;
import unquietcode.tools.logmachine.core.topics.Topic;

/**
 * @author Benjamin Fagin
 * @version 10-21-2012
 */
public abstract class LogMachine<T> extends BaseLogMachine<T> implements LogMachineBuilders<T> {

	protected LogMachine(T logger, LogHandler<T> handler) {
		super(logger, handler);
	}


	//==o==o==o==o==o==o==| logging methods |==o==o==o==o==o==o==//

	// This is the implementation of all the basic logging methods,
	// but it's a bit tedious.
	//
	// For a clearer picture, consider checking out the implemented
	// interfaces instead.



	// --- specific one-shots (SLF4J style) ---


	/**
	 * {@inheritDoc}
	 * @see LogMachineBuilders#error(String, Throwable)
	 */
	@Override
	public void error(String message, Throwable exception) {
		if (isError()) {
			genericBuilder().because(exception).error(message);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see LogMachineBuilders#error(String, Object[] data)
	 */
	@Override
	public void error(String message, Object...data) {
		if (isError()) {
			genericBuilder().error(message, data);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see LogMachineBuilders#warn(String, Throwable)
	 */
	@Override
	public void warn(String message, Throwable exception) {
		if (isWarn()) {
			genericBuilder().because(exception).warn(message);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see LogMachineBuilders#warn(String, Object[] data)
	 */
	@Override
	public void warn(String message, Object...data) {
		if (isWarn()) {
			genericBuilder().warn(message, data);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see LogMachineBuilders#info(String, Throwable)
	 */
	@Override
	public void info(String message, Throwable exception) {
		if (isInfo()) {
			genericBuilder().because(exception).info(message);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see LogMachineBuilders#info(String, Object[] data)
	 */
	@Override
	public void info(String message, Object...data) {
		if (isInfo()) {
			genericBuilder().info(message, data);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see LogMachineBuilders#debug(String, Throwable)
	 */
	@Override
	public void debug(String message, Throwable exception) {
		if (isDebug()) {
			genericBuilder().because(exception).debug(message);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see LogMachineBuilders#debug(String, Object[] data)
	 */
	@Override
	public void debug(String message, Object...data) {
		if (isDebug()) {
			genericBuilder().debug(message, data);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see LogMachineBuilders#trace(String, Throwable)
	 */
	@Override
	public void trace(String message, Throwable exception) {
		if (isTrace()) {
			genericBuilder().because(exception).trace(message);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see LogMachineBuilders#trace(String, Object[] data)
	 */
	@Override
	public void trace(String message, Object...data) {
		if (isTrace()) {
			genericBuilder().trace(message, data);
		}
	}

	//  --- generic builders ---


	/**
	 * {@inheritDoc}
	 * @see LogMachineBuilders#because(Throwable)
	 */
	@Override
	public GenericLogMachineBuilder_2debug_4f_2error_4f_2from_4f_2info_4f_2to_4f_2trace_4f_2warn_4f_2with_4f_2with_1A_4f<Void>
		because(Throwable cause)
	{
		return genericBuilder().because(cause);
	}

	/**
	 * {@inheritDoc}
	 * @see LogMachineBuilders#from(String)
	 */
	@Override
	public GenericLogMachineBuilder_2because_4f_2debug_4f_2error_4f_2info_4f_2to_4f_2trace_4f_2warn_4f_2with_4f_2with_1A_4f<Void>
		from(String location)
	{
		return genericBuilder().from(location);
	}

	/**
	 * {@inheritDoc}
	 * @see LogMachineBuilders#to(Topic...)
	 */
	@Override
	public GenericLogMachineBuilder_2because_4f_2debug_4f_2error_4f_2from_4f_2info_4f_2trace_4f_2warn_4f_2with_4f_2with_1A_4f<Void>
		to(Topic... topics)
	{
		return genericBuilder().to(topics);
	}

	/**
	 * {@inheritDoc}
	 * @see LogMachineBuilders#with(String, String)
	 */
	@Override
	public GenericLogMachineBuilder_2because_4f_2from_4f_2to_4f_2with_4f_2with_1A_4f<Void>
		with(String key, String value)
	{
		return genericBuilder().with(key, value);
	}

	/**
	 * {@inheritDoc}
	 * @see LogMachineBuilders#with(String, Number)
	 */
	@Override
	public GenericLogMachineBuilder_2because_4f_2from_4f_2to_4f_2with_4f_2with_1A_4f<Void>
		with(String key, Number value)
	{
		return genericBuilder().with(key, value);
	}
}