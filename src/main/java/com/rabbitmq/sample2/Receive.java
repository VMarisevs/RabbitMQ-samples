package com.rabbitmq.sample2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Receive {

	private final static String QUEUE_NAME = "task_queue";
	
	public static void main(String[] args) throws IOException {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		
		final Channel channel = connection.createChannel();
		
		
		
		
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		
		System.out.println(" [*] Waiting for message. To exit press CTRL+C");
		
		
		channel.basicQos(1);
		Consumer consumer = new DefaultConsumer(channel){
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException{
				String message = new String(body, "UTF-8");

				System.out.println(" [x] Received '" + message + "'");
			    try {
			    	doWork(message);
			    } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
			    	System.out.println(" [x] Done");
			    	/*
			    	 * Forgotten acknowledgment
			    	 * 
			    	 * It's a common mistake to miss the basicAck. It's an easy error, but the consequences are serious. Messages will be redelivered when your client quits (which may look like random redelivery), but RabbitMQ will eat more and more memory as it won't be able to release any unacked messages.
			    	 * 
			    	 * In order to debug this kind of mistake you can use rabbitmqctl to print the messages_unacknowledged field:
			    	 * 
			    	 * $ sudo rabbitmqctl list_queues name messages_ready messages_unacknowledged
			    	 * 
			    	 */
			    	
			    	channel.basicAck(envelope.getDeliveryTag(), false);
			    }
			}
		};
		
		channel.basicConsume(QUEUE_NAME, true, consumer);
		
	}
	
	private static void doWork(String task) throws InterruptedException {
	    for (char ch: task.toCharArray()) {
	        if (ch == '.') Thread.sleep(1000);
	    }
	}
}
