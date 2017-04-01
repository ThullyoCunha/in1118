package client;

import reclamacoes.IReclamacoes;
import reclamacoes.Reclamacao;
import rpc.ServiceProxy;
import storageservice.ConsistencyException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by tjamir on 30/03/17.
 */
public class ReclamacaoClient implements IReclamacoes{

    private ServiceProxy<IReclamacoes> reclamacoesServiceProxy;


    private RPCTCPClient rpcClient;

    public void init() throws IOException {
        rpcClient = new RPCTCPClient(1234, "127.0.0.1");
        rpcClient.init();
        reclamacoesServiceProxy = new ServiceProxy<>();
        reclamacoesServiceProxy.setServiceInterface(IReclamacoes.class);
        reclamacoesServiceProxy.setCallInvoker(rpcClient);
        reclamacoesServiceProxy.init();

    }

    @Override
    public Reclamacao reclamar(String texto) throws IOException, ConsistencyException {
        return reclamacoesServiceProxy.getService().reclamar(texto);
    }

    public static void main(String[] args) throws IOException, ConsistencyException {
        BufferedReader inFromUser=new BufferedReader(new InputStreamReader(System.in));
        ReclamacaoClient client=new ReclamacaoClient();
        client.init();
        String texto;
        while(!(texto=inFromUser.readLine()).equals("!")){
            Reclamacao reclamacao=client.reclamar(texto);
            System.out.println(String.format("Reclamacao registada: %d", reclamacao.getTimestamp()));
        }
    }


}
