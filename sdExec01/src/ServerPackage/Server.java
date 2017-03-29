package ServerPackage;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {

	private ServerSocket server;

	private List<Attendant> attendants;

	private boolean initialized;
	private boolean running;
	private Thread thread;

	// Server method that will call the connection opening method.
	public Server(int port) throws Exception {
		attendants = new ArrayList<Attendant>();
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

		for (Attendant attendant : attendants) {
			try {
				attendant.stop();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		try {
			server.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		server = null;
		initialized = false;
		running = false;
		thread = null;
	}

	// "start" method that will start the socket connection.
	public void start() {
		if (!initialized || running) {
			return;
		}

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	// "stop" method that will stop the socket connection.
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
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		close();
	}

	private void handleRequest(Socket socket) throws IOException {
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		OutputStreamWriter streamWriter = new OutputStreamWriter(socket.getOutputStream());
		String read;
		while((read=streamReader.readLine())!=null){
			System.out.println(read);
			streamWriter.write(read.toUpperCase() + "\n");
			streamWriter.flush();
		}
		streamWriter.close();
		streamReader.close();
		
		
	}

	public static void main(String[] args) throws Exception {

		new Server(3131).start();

	}

}
