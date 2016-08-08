package com.rabbitmq.sample4;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogDirect {

	public static void main(String[] args) throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		
		channel.exchangeDeclare(ExchangeName.getExchange(), "direct");
		
		String severity = args[0].toString(); // getSeverity(args);
		String msg = args[1].toString(); // getMessage(args);
		
		channel.basicPublish(ExchangeName.getExchange(), severity, null, msg.getBytes());
		System.out.println(" [x] Sent '" + severity + "' : '" + msg +"'");
		
		channel.close();
		connection.close();
	}	
	
}
