package unquietcode.tools.logmachine.core.topics;

/**
 * This interface facilitates the use of topics in log
 * events. By design, it can be applied to any enum to
 * automatically turn them into topics.
 *
 * LogMachine will treat any two topics with the same
 * {@link #name()} as identical.
 *
 * @author Ben Fagin
 * @version 2013-08-22
 */
public interface Topic {
	String name();
}
