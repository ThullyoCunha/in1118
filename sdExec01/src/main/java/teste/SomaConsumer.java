package teste;

/**
 * Created by tjamir on 01/04/17.
 */
public class SomaConsumer {

    private ISoma soma;

    public SomaConsumer(){
        soma = new Soma();
    }

    public void execute(){
        soma.soma(1,2);
    }
}
