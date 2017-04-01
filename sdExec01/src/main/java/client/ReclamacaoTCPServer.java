package client;

import reclamacoes.IReclamacoes;
import reclamacoes.ReclamacoesImpl;
import rpc.ServiceHandler;
import server.TCPServer;
import worker.AbstractSocketWorker;
import worker.RPCWorker;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by tjamir on 30/03/17.
 */
public class ReclamacaoTCPServer extends TCPServer {

    ServiceHandler<IReclamacoes> serviceHandler;

    public ReclamacaoTCPServer(int port) throws Exception {
        super(port);
    }

    public void init() throws IOException {
        ReclamacoesImpl reclamacoes = new ReclamacoesImpl();
        reclamacoes.init();
        serviceHandler = new ServiceHandler<>(reclamacoes, IReclamacoes.class);
        this.start();

    }
    @Override
    public AbstractSocketWorker buildWorker(Socket socket) throws Exception {
        return new RPCWorker<>(socket, serviceHandler);
    }

    public static void main(String [] args) throws Exception {
        ReclamacaoTCPServer reclamacaoServer = new ReclamacaoTCPServer(1234);
        reclamacaoServer.init();
    }
}
