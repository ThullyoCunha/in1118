package client.amqp;

import client.tcp.ReclamacaoClient;
import reclamacoes.IReclamacoes;
import reclamacoes.Reclamacao;
import rpc.ServiceProxy;
import storageservice.ConsistencyException;
import storageservice.StorageService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

/**
 * Created by tjamir on 03/04/17.
 */
public class ReclamacaoAMQPClient implements IReclamacoes {

    private RPCAMQPClient rpcClient;

    private ServiceProxy<IReclamacoes> service;

    private void stop() throws IOException {
        rpcClient.stop();
    }

    public void init() throws IOException {
        rpcClient = new RPCAMQPClient("reclamacoes.queue");
        try {
            rpcClient.init();
        } catch (TimeoutException e) {
            throw new IOException(e);
        }
        service = new ServiceProxy<>();
        service.setServiceInterface(IReclamacoes.class);
        service.setCallInvoker(rpcClient);
        service.init();
    }

    @Override
    public Reclamacao reclamar(String texto) throws IOException, ConsistencyException {
        return service.getService().reclamar(texto);
    }

    public static void main(String[] args) throws IOException, ConsistencyException {
        BufferedReader inFromUser=new BufferedReader(new InputStreamReader(System.in));
        ReclamacaoAMQPClient client=new ReclamacaoAMQPClient();
        client.init();
        String texto;
        while(!(texto=inFromUser.readLine()).equals("!")){
            Reclamacao reclamacao=client.reclamar(texto);
            System.out.println(String.format("Reclamacao registada: %d", reclamacao.getTimestamp()));
        }
    }


}
