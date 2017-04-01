package worker;

import rpc.Call;
import rpc.CallResult;
import rpc.ServiceHandler;
import worker.tcp.AbstractTCPWorker;

import java.io.*;
import java.net.Socket;

/**
 * Created by tjamir on 30/03/17.
 */
public class RPCWorker<T> extends AbstractTCPWorker {





    private ServiceHandler<T> proxy;

    public RPCWorker(Socket socket, ServiceHandler<T> proxy) throws Exception {
        super(socket);
        this.proxy=proxy;
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
