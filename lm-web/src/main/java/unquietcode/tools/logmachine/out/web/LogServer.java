package unquietcode.tools.logmachine.out.web;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class LogServer extends WebSocketServer {
	private int retention = 50;
	private final Queue<String> events = new ArrayDeque<String>();
	private boolean awaitConnection = false;    // if true, log events will block until there is at least one connection


	public LogServer(int port) {
		super(new InetSocketAddress(port));
	}

	public void setRetention(int count) {
		this.retention = count;
	}

	public void setAwaitConnection(boolean value) {
		this.awaitConnection = value;
	}

	public void newEvent(String message) {
		events.add(message);

		if (events.size() > retention) {
			events.remove();
		}

		writeEvent(message);
	}

	private void writeEvent(String message) {
		if (awaitConnection) {
			while (connections().isEmpty()) {
				try {
					Thread.sleep(10);
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}
		}

		for (WebSocket connection : connections()) {
			connection.send(message);
		}
	}

	// --------- WebSocket methods -----------------

	@Override
	public void onError(WebSocket connection, Exception ex) {
		ex.printStackTrace();
	}

	@Override
	public void onMessage(WebSocket connection, String message) {
		// nothing
	}

	@Override
	public void onClose(WebSocket connection, int code, String reason, boolean remote) {
		// nothing
	}

	@Override
	public void onOpen(WebSocket connection, ClientHandshake handshake) {
		// write out all of the existing events
		Queue<String> events = new ArrayDeque<String>(this.events);

		for (String event : events) {
			connection.send(event);
		}

		// TODO this could potentially write out an event which was already written, could it?
		// I think so, because it could connect here and then be part of the connection pool which written
	}
}
