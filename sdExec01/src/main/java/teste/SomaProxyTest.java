package teste;

import rpc.ServiceHandler;
import rpc.ServiceProxy;

/**
 * Created by tjamir on 30/03/17.
 */
public class SomaProxyTest {

    public static void main(String [] args){
        ServiceProxy<ISoma> somaServiceProxy=new ServiceProxy<>();
        somaServiceProxy.setServiceInterface(ISoma.class);
        somaServiceProxy.setCallInvoker(new ServiceHandler<>(new Soma(), ISoma.class));
        somaServiceProxy.init();
        System.out.println(somaServiceProxy.getService().soma(1, 2));
    }
}
