package unquietcode.tools.logmachine.core;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ben Fagin
 * @version 11-17-2012
 */
public class GroupingLogHandler<T> implements LogHandler<T> {
	public static final String GROUP_LOGGER = "*LM*";
	private final LogHandler<T> handler;
	private final T[] delegates;

	public GroupingLogHandler(LogHandler<T> handler, T...delegates) {
		this.handler = checkNotNull(handler);
		this.delegates = checkNotNull(delegates);
	}

	/*
		Find the aggregate level of all handlers.
		The level will be the greatest level (finest grained).
	 */
	private Level aggregateLevel(T base) {
		Level level = handler.getLevel(base);

		for (T delegate : delegates) {
			Level _level = handler.getLevel(delegate);

			if (_level.isFinerThan(level)) {
				level = _level;
			}
		}

		return level;
	}

	@Override
	public void logEvent(T logger, LogEvent e) {
		Level level = aggregateLevel(logger);

		if (level.isFinerOrEqual(e.getLevel())) {
			handler.logEvent(logger, e);
		}
	}

	@Override
	public Level getLevel(T logger) {
		return aggregateLevel(logger);
	}

	@Override
	public String getLoggerName(T logger) {
		return handler.getLoggerName(logger);
	}

	@Override
	public boolean isError(T logger) {
		return aggregateLevel(logger).isFinerOrEqual(Level.ERROR);
	}

	@Override
	public boolean isWarn(T logger) {
		return aggregateLevel(logger).isFinerOrEqual(Level.WARN);
	}

	@Override
	public boolean isInfo(T logger) {
		return aggregateLevel(logger).isFinerOrEqual(Level.INFO);
	}

	@Override
	public boolean isDebug(T logger) {
		return aggregateLevel(logger).isFinerOrEqual(Level.DEBUG);
	}

	@Override
	public boolean isTrace(T logger) {
		return aggregateLevel(logger).isFinerOrEqual(Level.TRACE);
	}
}
