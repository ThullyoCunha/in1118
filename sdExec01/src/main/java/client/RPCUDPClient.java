package client;

import rpc.Call;
import rpc.CallInvoker;
import rpc.CallResult;

import java.io.*;

/**
 * Created by tjamir on 30/03/17.
 */
public class RPCUDPClient extends UDPClient implements CallInvoker {

    public RPCUDPClient(int port, String ip) {
        super(ip, port);
    }

    @Override
    public CallResult invoke(Call call) {
        try {
            return deserialize(sendMessage(serialize(call)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private byte[] serialize(Call call) throws IOException {
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(byteArrayOutputStream)){
            objectOutputStream.writeObject(call);
            return byteArrayOutputStream.toByteArray();
        }
    }

    private CallResult deserialize(byte[] param) throws IOException, ClassNotFoundException {
        try(ByteArrayInputStream inputStream = new ByteArrayInputStream(param);
            ObjectInputStream objectInputStream=new ObjectInputStream(inputStream)) {
            return (CallResult) objectInputStream.readObject();
        }

    }
}
