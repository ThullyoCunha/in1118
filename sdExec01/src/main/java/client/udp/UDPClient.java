package client.udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by tjamir on 3/30/17.
 */
public class UDPClient{


    private String server;
    private int port;


    public UDPClient(String server, int port) {
        this.server = server;
        this.port = port;
    }

    public byte[] sendMessage(byte [] sendData) throws IOException {
        try {

            DatagramSocket clientSocket = new DatagramSocket();

            InetAddress IPAddress = InetAddress.getByName(server);

            byte[] receiveData = new byte[1024];


            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

            clientSocket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            clientSocket.receive(receivePacket);

            receiveData = receivePacket.getData();


            clientSocket.close();
            return receiveData;

        } catch (Exception e) {
            throw new IOException(e);
        }
    }

}
