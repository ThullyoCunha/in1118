package worker.amqp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.rabbitmq.client.Channel;

import rpc.Call;
import rpc.CallResult;
import rpc.ServiceHandler;

public class RPCAMQPWorker <T> extends AMQPWorker {
	
	private ServiceHandler<T> proxy;

	public RPCAMQPWorker(Channel channel, ServiceHandler<T> proxy) {
		super(channel);
		this.proxy = proxy;
	}

	@Override
	public byte[] handle(byte[] param) throws ClassNotFoundException, IOException {
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