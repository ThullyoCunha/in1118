package client.amqp;

import client.tcp.StorageTCPServer;
import rpc.ServiceHandler;
import server.AMQPServer;
import storageservice.LocalStorageServiceImpl;
import storageservice.StorageService;
import worker.RPCWorker;
import worker.amqp.AMQPWorker;
import worker.amqp.RPCAMQPWorker;
import worker.tcp.AbstractTCPWorker;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeoutException;

/**
 * Created by tjamir on 03/04/17.
 */
public class StorageAMQPServer extends AMQPServer{

    private ServiceHandler serviceHandler;

    public StorageAMQPServer(String rpcQueueName) {
        super(rpcQueueName);
    }

    public void init() throws IOException, TimeoutException {
        serviceHandler = new ServiceHandler<>(new LocalStorageServiceImpl(), StorageService.class);
        this.start();

    }

    @Override
    public AMQPWorker buildWorker() {
        return new RPCAMQPWorker<>(this.channel, serviceHandler);
    }

    public static void main(String[] args) throws Exception {
        StorageAMQPServer storageServer=new StorageAMQPServer("storage.queue");
        storageServer.init();
    }
}
