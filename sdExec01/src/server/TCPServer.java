package server;

import worker.AbstractSocketWorker;

import java.net.ServerSocket;
import java.net.Socket;

public abstract class TCPServer implements Runnable {

	private ServerSocket server;

	private boolean initialized;
	private boolean running;
	private Thread thread;

	// TCPServer method that will call the connection opening method.
	public TCPServer(int port) throws Exception {
		initialized = false;
		running = false;

		open(port);
	}

	// "open" method that will create the socket connection.
	private void open(int port) throws Exception {
		server = new ServerSocket(port);
		initialized = true;
	}

	// "close" method that will closed the socket connection.
	private void close() {

		try {
			server.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		server = null;
		initialized = false;
		running = false;
		thread = null;
	}

	// "start" method that will start the thread.
	public void start() {
		if (!initialized || running) {
			return;
		}

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	// "stop" method that will stop the thread.
	public void stop() throws Exception {
		if (!initialized || running) {
			return;
		}

		running = false;
		thread.join();
	}

	// "run" method that will maintain the socket connection.
	@Override
	public void run() {
		System.out.println("Waiting for connection");

		while (running) {
			Socket socket = null;
			try {
				socket = server.accept();
				System.out.println("Connection OK");
				handleRequest(socket);

			} catch (Exception e) {
				e.printStackTrace();
				break;
			}finally{

			}
		}

		close();
	}

	private void handleRequest(Socket socket) throws Exception {

		AbstractSocketWorker worker=buildWorker(socket);
		worker.start();

	}


	public abstract AbstractSocketWorker buildWorker(Socket socket) throws Exception;


}