package worker.amqp;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import worker.AbstractWoker;

public abstract class AMQPWorker extends DefaultConsumer implements AbstractWoker{

	private Channel channel;

	public AMQPWorker(Channel channel) {
		super(channel);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
		AMQP.BasicProperties replyProps = new AMQP.BasicProperties
	    .Builder()
	    .correlationId(properties.getCorrelationId())
	    .build();
	        
			byte[] response;
			try {
				response = this.handle(body);
				
				channel.basicPublish( "", properties.getReplyTo(), replyProps, response);
				
		        channel.basicAck(envelope.getDeliveryTag(), false);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}