package unquietcode.tools.logmachine.impl.logback;

import ch.qos.logback.core.boolex.EvaluationException;
import ch.qos.logback.core.boolex.EventEvaluatorBase;
import ch.qos.logback.core.filter.EvaluatorFilter;
import ch.qos.logback.core.spi.FilterReply;
import unquietcode.tools.logmachine.helpers.DeduplicatingFilterHelper;

/**
 * @author Ben Fagin
 * @version 02-06-2013
 */
public class LogbackDeduplicatingFilter<_Event> extends EvaluatorFilter<_Event> {

	public LogbackDeduplicatingFilter() {
		onMatch = FilterReply.DENY;
		onMismatch = FilterReply.NEUTRAL;

		EventTrackingEvaluator<_Event> evaluator = new EventTrackingEvaluator<_Event>();
		setEvaluator(evaluator);
		evaluator.start();
	}

	private static class EventTrackingEvaluator<_Event> extends EventEvaluatorBase<_Event> {
		private final DeduplicatingFilterHelper helper = new DeduplicatingFilterHelper();

		public @Override boolean evaluate(_Event event) throws NullPointerException, EvaluationException {
			return helper.isDuplicate(event);
		}
	}
}
