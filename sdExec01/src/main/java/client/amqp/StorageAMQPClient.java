package client.amqp;

import client.tcp.RPCTCPClient;
import rpc.ServiceProxy;
import storageservice.ConsistencyException;
import storageservice.StorageService;
import storageservice.ValueObject;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by tjamir on 03/04/17.
 */
public class StorageAMQPClient implements StorageService{

    private ServiceProxy<StorageService> service;

    private RPCAMQPClient rpcClient;


    @Override
    public ValueObject storePair(String key, ValueObject value) throws ConsistencyException {
        return service.getService().storePair(key, value);
    }

    @Override
    public ValueObject getValue(String key) {
        return service.getService().getValue(key);
    }

    private void stop() throws IOException {
        rpcClient.stop();
    }

    public void init() throws IOException {
        rpcClient = new RPCAMQPClient("storage.queue");
        try {
            rpcClient.init();
        } catch (TimeoutException e) {
            throw new IOException(e);
        }
        service = new ServiceProxy<>();
        service.setServiceInterface(StorageService.class);
        service.setCallInvoker(rpcClient);
        service.init();
    }

    public static void main(String[] args) throws IOException, ConsistencyException, TimeoutException {

        StorageAMQPClient client=new StorageAMQPClient();
        client.init();
        ValueObject vo=new ValueObject();
        vo.setVersion(System.currentTimeMillis());
        vo.setContent(new byte[1024]);
        client.storePair("teste", vo);
        vo=client.storePair("teste", vo);
        System.out.println(vo.getVersion() +" salvo com sucesso");
        client.stop();

    }


}
