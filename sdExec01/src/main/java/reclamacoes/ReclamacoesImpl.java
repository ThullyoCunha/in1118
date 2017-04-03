package reclamacoes;

import client.KindOfTransport;
import client.tcp.StorageClient;
import storageservice.ConsistencyException;
import storageservice.StorageService;
import storageservice.ValueObject;

import java.io.*;

/**
 * Created by tjamir on 30/03/17.
 */
public class ReclamacoesImpl implements IReclamacoes {


    private StorageService storageService;

    public void init(KindOfTransport kindOfTransport) throws IOException {
        StorageService client=new StorageServiceFactory(kindOfTransport).getStorageService();
        storageService=client;

    }

    @Override
    public Reclamacao reclamar(String texto) throws IOException, ConsistencyException {
        Reclamacao reclamacao=new Reclamacao(texto, System.currentTimeMillis());
        storageService.storePair(Long.toString(reclamacao.getTimestamp()), serialize(reclamacao));
        return reclamacao;

    }

    private ValueObject serialize(Reclamacao reclamacao) throws IOException {
        ValueObject valueObject=new ValueObject();
        try(ByteArrayOutputStream baos=new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(baos)){
            objectOutputStream.writeObject(reclamacao);
            valueObject.setContent(baos.toByteArray());
            return valueObject;
        }
    }
}
