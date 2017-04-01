package server;

import java.net.Socket;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;

import worker.amqp.AMQPWorker;
import worker.tcp.AbstractTCPWorker;

public abstract class AMQPServer {
	
	private static final String RPC_QUEUE_NAME = "rpc_queue";

    public void runService(String[] argv) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = null;
        try {
            connection      = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);

            channel.basicQos(1);

            System.out.println(" [x] Awaiting RPC requests");

          

            channel.basicConsume(RPC_QUEUE_NAME, false, buildWorker());

            //...
        } catch (Exception e){
        	e.printStackTrace();
        }
    }
    public abstract AMQPWorker buildWorker();    
}