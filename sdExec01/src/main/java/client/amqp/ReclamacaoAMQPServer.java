package client.amqp;

import client.KindOfTransport;
import reclamacoes.IReclamacoes;
import reclamacoes.ReclamacoesImpl;
import rpc.ServiceHandler;
import server.AMQPServer;
import storageservice.LocalStorageServiceImpl;
import storageservice.StorageService;
import worker.amqp.AMQPWorker;
import worker.amqp.RPCAMQPWorker;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by tjamir on 03/04/17.
 */
public class ReclamacaoAMQPServer extends AMQPServer {

    private ServiceHandler serviceHandler;

    public ReclamacaoAMQPServer(String rpcQueueName) {
        super(rpcQueueName);
    }

    public void init() throws IOException, TimeoutException {
        ReclamacoesImpl reclamacoes = new ReclamacoesImpl();
        reclamacoes.init(KindOfTransport.AMQP);
        serviceHandler = new ServiceHandler<>(reclamacoes, IReclamacoes.class);
        this.start();

    }

    @Override
    public AMQPWorker buildWorker() {
        return new RPCAMQPWorker<>(channel, serviceHandler);
    }

    public static void main(String [] args) throws IOException, TimeoutException {
        ReclamacaoAMQPServer reclamacaoAMQPServer=new ReclamacaoAMQPServer("reclamacoes.queue");
        reclamacaoAMQPServer.init();
    }
}
