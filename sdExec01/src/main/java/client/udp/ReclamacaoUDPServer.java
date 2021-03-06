package client.udp;

import client.KindOfTransport;
import reclamacoes.IReclamacoes;
import reclamacoes.ReclamacoesImpl;
import rpc.ServiceHandler;
import server.UDPServer;
import worker.udp.AbstractUDPWorker;
import worker.RPCUDPWorker;

import java.io.IOException;
import java.net.DatagramSocket;

/**
 * Created by tjamir on 30/03/17.
 */
public class ReclamacaoUDPServer extends UDPServer {

    ServiceHandler<IReclamacoes> serviceHandler;

    public ReclamacaoUDPServer(int port) throws Exception {
        super(port);
    }

    @Override
    public AbstractUDPWorker buildWorker(DatagramSocket socket) {
        return new RPCUDPWorker<>(socket, serviceHandler);
    }

    public void init() throws IOException {
        ReclamacoesImpl reclamacoes = new ReclamacoesImpl();
        reclamacoes.init(KindOfTransport.UDP);
        serviceHandler = new ServiceHandler<>(reclamacoes, IReclamacoes.class);
        this.start();

    }


    public static void main(String [] args) throws Exception {
        ReclamacaoUDPServer reclamacaoServer = new ReclamacaoUDPServer(1235);
        reclamacaoServer.init();
    }
}
