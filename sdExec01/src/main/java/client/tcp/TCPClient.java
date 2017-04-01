package client.tcp;


import java.io.*;
import java.net.Socket;

/**
 * Created by tjamir on 30/03/17.
 */
public class TCPClient {

    private int port;

    private String ip;


    public TCPClient(int port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    private DataInputStream in;

    private DataOutputStream out;
    private Socket socket;


    public void init() throws IOException {
        socket = new Socket(ip, port);

        in= new DataInputStream(socket.getInputStream());
        out= new DataOutputStream(socket.getOutputStream());

    }

    public void close() throws IOException {
        out.write(-1);
        out.close();
        in.close();
        socket.close();

    }


    public byte[] sendMessage(byte [] data) throws IOException {




        out.writeInt(data.length); // write length of the message
        out.write(data);           // write the message
        out.flush();


        int messageSize = in.readInt();
        if(messageSize>0) {
            data = new byte[messageSize];
            //int read = 0;
            //read=in.read(data, 0, messageSize);
            in.readFully(data);
            return data;
        }
        return null;


    }



}
