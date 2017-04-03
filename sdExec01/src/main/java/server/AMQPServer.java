package server;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import worker.amqp.AMQPWorker;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public abstract class AMQPServer {
	
	private String rpcQueueName;
    protected Channel channel;
    private Connection connection;


    public AMQPServer(String rpcQueueName) {
        this.rpcQueueName = rpcQueueName;
    }

    public void runService() {


        connection = null;
        try {

            channel.basicConsume(rpcQueueName, false, buildWorker());

            //...
        } catch (Exception e){
        	e.printStackTrace();
        }
    }
    public abstract AMQPWorker buildWorker();

    public void start() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.queueDeclare(rpcQueueName, false, false, false, null);

        channel.basicQos(1);

        new Thread(this::runService).start();
    }
}