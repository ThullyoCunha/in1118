package client;

import rpc.ServiceHandler;
import server.TCPServer;
import storageservice.LocalStorageServiceImpl;
import storageservice.StorageService;
import worker.AbstractSocketWorker;
import worker.RPCWorker;

import java.net.Socket;

/**
 * Created by tjamir on 30/03/17.
 */
public class StorageTCPServer extends TCPServer {
    private ServiceHandler serviceHandler;

    public StorageTCPServer(int port) throws Exception {
        super(port);
    }


    public void init(){
        serviceHandler = new ServiceHandler<>(new LocalStorageServiceImpl(), StorageService.class);
        this.start();

    }
    @Override
    public AbstractSocketWorker buildWorker(Socket socket) throws Exception {
        return new RPCWorker<>(socket, serviceHandler);
    }

    public static void main(String [] args) throws Exception {
        StorageTCPServer storageServer=new StorageTCPServer(9696);
        storageServer.init();
    }
}
