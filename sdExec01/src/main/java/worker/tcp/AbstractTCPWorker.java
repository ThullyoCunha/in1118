package worker.tcp;

import worker.AbstractWoker;

import java.io.*;
import java.net.Socket;

/**
 * Created by tjamir on 30/03/17.
 */
public abstract class AbstractTCPWorker  implements Runnable, AbstractWoker{

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean initialized;
    private boolean running;
    private Thread thread;


    public AbstractTCPWorker(Socket socket) throws Exception {
        this.socket = socket;
        this.initialized = false;
        this.running = false;

        open();
    }

    public void open() throws Exception {
        try{
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            initialized = true;
        } catch (Exception e){
            e.printStackTrace();
            close();
        }
    }

    public void close() throws Exception {
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


                int length = in.readInt();                    // read length of incoming message
                if(length>0) {
                    byte[] message = new byte[length];
                    in.readFully(message);;
                    //in.read(message, 0, message.length); // read the message
                    byte[] responseContent=this.handle(message);
                    out.writeInt(responseContent.length);
                    out.write(responseContent);
                    out.flush();
                }else if(length<0){
                    stop();
                }


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
