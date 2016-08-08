package com.rabbitmq.sample5;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogTopic {

	public static void main(String[] args) throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(ExchangeName.NAME, "topic");
		
		String routingKey = args[0].toString();
		String message = args[1].toString();
		
		channel.basicPublish(ExchangeName.NAME, routingKey, null, message.getBytes());
		
		System.out.println(" [x] Sent '" + routingKey + "' : '" + message + "'");
		
		connection.close();
	}
}
