package worker;

import rpc.Call;
import rpc.CallResult;
import rpc.ServiceHandler;

import java.io.*;
import java.net.DatagramSocket;

/**
 * Created by tjamir on 30/03/17.
 */
public class RPCUDPWorker<T>  extends AbstractUDPWorker {

    private ServiceHandler<T> proxy;

    public RPCUDPWorker(DatagramSocket socket, ServiceHandler<T> proxy) {
        super(socket);
        this.proxy = proxy;
    }



    @Override
    public byte[] handle(byte[] param) throws IOException {

        Call call= null;
        CallResult callResult;
        try {
            call = deserialize(param);
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
        callResult = proxy.invoke(call);
        return serialize(callResult);

    }

    private byte[] serialize(CallResult callResult) throws IOException {
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(byteArrayOutputStream)){
            objectOutputStream.writeObject(callResult);
            return byteArrayOutputStream.toByteArray();
        }
    }

    private Call deserialize(byte[] param) throws IOException, ClassNotFoundException {
        try(ByteArrayInputStream inputStream = new ByteArrayInputStream(param);
            ObjectInputStream objectInputStream=new ObjectInputStream(inputStream)) {
            return (Call) objectInputStream.readObject();
        }

    }
}
