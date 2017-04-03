package client.tcp;

import rpc.ServiceProxy;
import storageservice.ConsistencyException;
import storageservice.StorageService;
import storageservice.ValueObject;

import java.io.IOException;

/**
 * Created by tjamir on 30/03/17.
 */
public class StorageClient implements StorageService{


    @Override
    public ValueObject storePair(String key, ValueObject value) throws ConsistencyException {
        return service.getService().storePair(key, value);
    }

    @Override
    public ValueObject getValue(String key) {
        return service.getService().getValue(key);
    }

    private ServiceProxy<StorageService> service;


    private RPCTCPClient rpcClient;

    public void init() throws IOException {
        rpcClient = new RPCTCPClient(9696, "127.0.0.1");
        rpcClient.init();
        service = new ServiceProxy<>();
        service.setServiceInterface(StorageService.class);
        service.setCallInvoker(rpcClient);
        service.init();
    }

    public static void main(String[] args) throws IOException, ConsistencyException {

        StorageClient client=new StorageClient();
        client.init();
        ValueObject vo=new ValueObject();
        vo.setVersion(System.currentTimeMillis());
        vo.setContent(new byte[1024]);



    }


}
