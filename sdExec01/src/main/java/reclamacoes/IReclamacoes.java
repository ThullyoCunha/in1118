package reclamacoes;

import storageservice.ConsistencyException;

import java.io.IOException;

/**
 * Created by tjamir on 30/03/17.
 */
public interface IReclamacoes {

    public Reclamacao reclamar(String texto) throws IOException, ConsistencyException;



}
