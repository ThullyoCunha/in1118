package client;

import rpc.ServiceHandler;
import server.TCPServer;
import server.UDPServer;
import storageservice.LocalStorageServiceImpl;
import storageservice.StorageService;
import worker.AbstractUDPWorker;
import worker.RPCUDPWorker;

import java.net.DatagramSocket;

/**
 * Created by tjamir on 30/03/17.
 */
public class StorageUDPServer extends UDPServer{

    private ServiceHandler serviceHandler;

    public StorageUDPServer(int port) throws Exception {
        super(port);
    }


    public void init(){
        serviceHandler = new ServiceHandler<>(new LocalStorageServiceImpl(), StorageService.class);
        this.start();

    }

    @Override
    public AbstractUDPWorker buildWorker(DatagramSocket socket) {
        return new RPCUDPWorker<>(socket, serviceHandler);
    }

    public static void main(String [] args) throws Exception {
        StorageUDPServer storageServer=new StorageUDPServer(9697);
        storageServer.init();
    }
}
