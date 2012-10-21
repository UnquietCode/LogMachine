package unquietcode.tools.logmachine.implementations.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import unquietcode.tools.logmachine.LogServer;

import java.io.IOException;

/**
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class LogServerAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
	private LogServer server;
	private int port = 9000;
	private int retention = 45;
	private boolean awaitConnection = false;
	private AbstractLogbackEncoder encoder = new JSONLogbackEncoder();


	public void setPort(int port) {
		this.port = port;
	}

	public void setRetention(int count) {
		this.retention = count;
	}

	public void setAwaitConnection(boolean value) {
		awaitConnection = value;
	}

	@Override
	public void start() {
		server = new LogServer(port);
		server.setRetention(retention);
		server.setAwaitConnection(awaitConnection);
		server.start();

		super.start();
	}

	@Override
	public void stop() {
		super.stop();

		try {
			server.stop();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		server = null;
	}

	@Override
	protected void append(ILoggingEvent event) {
		String message = encoder.doLayout(event);
		server.newEvent(message);
	}
}
