package reclamacoes;

import java.io.Serializable;

/**
 * Created by tjamir on 30/03/17.
 */
public class Reclamacao implements Serializable{

    public Reclamacao(String textp, long timestamp) {
        this.texto = textp;
        this.timestamp = timestamp;
    }

    private String texto;

    private long timestamp;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
