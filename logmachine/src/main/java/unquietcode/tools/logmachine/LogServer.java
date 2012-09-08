package unquietcode.tools.logmachine;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import unquietcode.tools.logmachine.LogEvent;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @author Ben Fagin
 * @version 08-05-2012
 */
public class LogServer extends WebSocketServer {
	private int retention = 50;
	private final Queue<String> events = new ArrayDeque<String>();

	public LogServer(int port) {
		super(new InetSocketAddress(port));
	}

	public void setRetention(int count) {
		this.retention = count;
	}

	public void newEvent(String message) {
		events.add(message);

		if (events.size() > retention) {
			events.remove();
		}

		writeEvent(message);
	}

	private void writeEvent(String message) {
		for (WebSocket connection : connections()) {
			connection.send(message);
		}
	}

	// --------- WebSocket methods -----------------

	@Override
	public void onError(WebSocket conn, Exception ex) {
		ex.printStackTrace();
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		// nothing
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		// nothing
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		// nothing
	}
}
