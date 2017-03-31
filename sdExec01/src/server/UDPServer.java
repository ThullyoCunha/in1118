
/**
 * Created by tjamir on 30/03/17.
 */
package server;

        import worker.AbstractUDPWorker;

        import java.io.*;
        import java.net.*;

/**
 * Created by tjamir on 3/30/17.
 */

public abstract class UDPServer implements Runnable{



    private boolean initialized;
    private boolean running;
    private Thread thread;
    private DatagramSocket serverS;

    // Server method that will call the connection opening method.
    public UDPServer(int port) throws Exception {
        initialized = false;
        running = false;

        open(port);
    }

    // "open" method that will create the socket connection.
    private void open(int port) throws Exception {
        serverS = new DatagramSocket(port);

        initialized = true;
    }

    // "close" method that will closed the socket connection.
    private void close() {

        try {
            serverS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        serverS = null;
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
    private void stop() throws Exception {
        if (!initialized || running) {
            return;
        }

        running = false;
        thread.join();
    }

    // "run" method that will maintain the socket connection.
    public void run() {
        System.out.println("Waiting for connection");

        while (running) {

            try {
                System.out.println("Connection OK");
                handleRequest(serverS);
                stop();
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        close();
    }

    private void handleRequest(DatagramSocket socket) throws IOException {

        AbstractUDPWorker worker= buildWorker(socket);
        worker.run();

    }

    public abstract AbstractUDPWorker buildWorker(DatagramSocket socket);



}