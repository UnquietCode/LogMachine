package unquietcode.tools.logmachine.impl.web;

import unquietcode.tools.logmachine.core.LogEvent;
import unquietcode.tools.logmachine.core.appenders.Appender;
import unquietcode.tools.logmachine.core.formats.Format;
import unquietcode.tools.logmachine.core.formats.JSONFormat;

import java.io.IOException;

/**
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class LogServerAppender implements Appender {
	private static final Format FORMATTER = new JSONFormat();
	private LogServer server;
	private int port = 9000;
	private int retention = 45;
	private boolean awaitConnection = false;

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
	}

	@Override
	public void stop() {
		try {
			server.stop();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		server = null;
	}

	@Override
	public void append(LogEvent event) {
		LogServer _server = server;

		if (_server != null) {
			StringBuilder message = FORMATTER.format(event);
			_server.newEvent(message.toString());
		}
	}
}
