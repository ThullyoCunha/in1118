package worker.udp;

import worker.AbstractWoker;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by tjamir on 30/03/17.
 */
public abstract class AbstractUDPWorker extends AbstractWoker implements Runnable{

    private DatagramSocket socket;

    public AbstractUDPWorker(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void open() throws Exception {

    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public void start() {
        new Thread(this).start();

    }

    @Override
    public void stop() throws Exception {

    }

    @Override
    public void run() {
        try {
        byte[] receiveData = new byte[10240];

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);
        byte[] data=receivePacket.getData();
        byte[] response=this.handle(data);

        InetAddress IPAddress = receivePacket.getAddress();
        int port = receivePacket.getPort();
        DatagramPacket sendPacket = new DatagramPacket(response, response.length, IPAddress, port);

        socket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
