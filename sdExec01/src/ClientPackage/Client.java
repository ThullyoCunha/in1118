package ClientPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketAddress;

public class Client implements Runnable{

	@Override
	public void run() {
			try {
				Socket socket = new Socket("127.0.0.1", 3131);
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				outputStreamWriter.write("Hello");
				outputStreamWriter.write("Hello again \n");
				outputStreamWriter.flush();
				
				
				System.out.println("Reply from server: "+reader.readLine());
				outputStreamWriter.close();
				socket.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		new Client().run();
	}

}
