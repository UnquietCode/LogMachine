package unquietcode.tools.logmachine.helpers;

/**
 * We assume that a single thread is extremely unlikely to produce multiple events
 * back-to-back from one package and N topics, which share the same identity hash value.
 * It is, of course, possible in theory, but the risk should be low enough, and the
 * consequence seems minor (exceedingly rare duplicate slips through).
 *
 * TODO Do multiple loggers produce distinct event objects? In that case, this will fail
 *      and we will need to use a different key than identity hash.
 *
 * @author Ben Fagin
 * @version 02-17-2013
 */
public class DeduplicatingFilterHelper {
	private final ThreadLocal<Integer> previousEvents = new ThreadLocal<Integer>();

	public boolean isDuplicate(Object event) {
		if (event == null) { return false; }
		int currentHash = System.identityHashCode(event);
		Integer previousHash = previousEvents.get();

		if (previousHash != null && previousHash.equals(currentHash)) {
			return true;
		} else {
			previousEvents.set(currentHash);
			return false;
		}
	}
}
