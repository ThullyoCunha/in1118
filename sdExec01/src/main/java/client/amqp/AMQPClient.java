package client.amqp;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

/**
 * Created by tjamir on 03/04/17.
 */
public class AMQPClient {



    private Connection connection;
    private Channel channel;
    private String requestQueueName;
    private DefaultConsumer consumer;


    public AMQPClient(String requestQueueName) {
        this.requestQueueName = requestQueueName;
    }

    public void init() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        connection = factory.newConnection();
        channel = connection.createChannel();

    }

    public byte[] sendMessage(byte[] data) throws IOException, InterruptedException {
        String corrId = UUID.randomUUID().toString();
        String replyQueueName = channel.queueDeclare().getQueue();
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();
        channel.basicPublish("", requestQueueName, props, data);
        final BlockingQueue<byte[]> response = new ArrayBlockingQueue<>(1);
        consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                if (properties.getCorrelationId().equals(corrId)) {
                    response.offer(body);
                }
            }
        };
        channel.basicConsume(replyQueueName, true, consumer);
        return response.take();
    }

    public void stop() throws IOException {
       connection.close();
    }
}
