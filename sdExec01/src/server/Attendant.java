package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Attendant implements Runnable{
	private Socket socket;
	private BufferedReader in;
	private PrintStream out;
	private boolean initialized;
	private boolean running;
	private Thread thread;
	

	private Attendant(Socket socket) throws Exception {
		this.socket = socket;
		this.initialized = false;
		this.running = false;
		
		open();
	}
	
	private void open() throws Exception {
		try{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintStream(socket.getOutputStream());
			initialized = true;
		} catch (Exception e){
			e.printStackTrace();
			close();
		}
	}

	private void close() throws Exception {
		if (in != null){
			try{
				in.close();
 			} catch (Exception e){
 				e.printStackTrace();
 			}
		}
		
		if (out != null){
			try{
				out.close();
 			} catch (Exception e){
 				e.printStackTrace();
 			}
		}
		
		try{
			socket.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		in = null;
		out = null;
		socket = null;
		initialized = false;
		running = false;
		thread = null;
	}
	
	public void start(){
		if (!initialized || running){
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void stop() throws Exception{
		running = false;
		thread.join();
	}
	
	@Override
	public void run() {
		while (running) {
			try{
				String message = in.readLine();
				
				System.out.println("Message received from UDPClient [" + socket.getInetAddress().getHostName() + ":" +
						socket.getPort() + "]: " + message);
				
				if("FIM".equals(message)){
					break;
				}
				
				out.println(message);
			} catch (Exception e){
				e.printStackTrace();
				break;
			}
		}
		
		try {
			close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}