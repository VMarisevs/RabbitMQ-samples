package com.rabbitmq.sample5;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ReceiveLogsTopic {

	public static void main(String[] args) throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(ExchangeName.NAME, "topic");
		String queueName = channel.queueDeclare().getQueue();
		
		if (args.length < 1){
			System.err.println("Usage: ReceiveLogsTopic [binding_key]...");
			System.exit(1);
		}
		
		for (String bindingKey : args){
			channel.queueBind(queueName, ExchangeName.NAME, bindingKey);
		}
		
		
		System.out.println(" [*] Waiting for messages. To exit press CTRL + C");
		
		Consumer consumer = new DefaultConsumer(channel){
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException{
			
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + envelope.getRoutingKey() + "' : '" + message + "'");
			}
		};
		channel.basicConsume(queueName, true, consumer);
	}
}
