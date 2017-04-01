package reclamacoes;

import client.KindOfTransport;
import client.tcp.StorageClient;
import client.udp.StorageUDPClient;
import storageservice.StorageService;

import java.io.IOException;

/**
 * Created by tjamir on 01/04/17.
 */
public class StorageServiceFactory {


    private StorageService storageService;

    private KindOfTransport kindOfTransport;

    public StorageServiceFactory(KindOfTransport kindOfTransport){
        this.kindOfTransport = kindOfTransport;
    }


    public synchronized StorageService getStorageService() throws IOException {
        if(storageService==null){
            storageService = buildStorageService();

        }
        return storageService;
    }

    private StorageService buildStorageService() throws IOException {
        StorageService storageService=null;
        switch (kindOfTransport){

            case TCP:
                StorageClient storageClient = new StorageClient();
                storageClient.init();
                storageService= storageClient;
                break;
            case UDP:
                StorageUDPClient storageUDPClient = new StorageUDPClient();
                storageUDPClient.init();
                storageService= storageUDPClient;
                break;
            case AMQP:
                storageService=null;
                break;
        }
        return storageService;
    }


}
