package client.udp;

import rpc.ServiceProxy;
import storageservice.ConsistencyException;
import storageservice.StorageService;
import storageservice.ValueObject;

import java.io.IOException;

/**
 * Created by tjamir on 30/03/17.
 */
public class StorageUDPClient implements StorageService {

    public ValueObject storePair(String key, ValueObject value) throws ConsistencyException {
        return service.getService().storePair(key, value);
    }

    @Override
    public ValueObject getValue(String key) {
        return service.getService().getValue(key);
    }

    private ServiceProxy<StorageService> service;


    private RPCUDPClient rpcClient;

    public void init() throws IOException {
        rpcClient = new RPCUDPClient(9697, "127.0.0.1");
        service = new ServiceProxy<>();
        service.setServiceInterface(StorageService.class);
        service.setCallInvoker(rpcClient);
        service.init();
    }

    public static void main(String[] ags) throws ConsistencyException, IOException {

        StorageUDPClient client = new StorageUDPClient();
        client.init();
         ValueObject vo = new ValueObject();
        vo.setVersion(System.currentTimeMillis());
        vo.setContent(new byte[256]);
        client.storePair("teste",vo);
}



}
