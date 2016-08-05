package com.rabbitmq.sample2;

import java.io.IOException;

import org.springframework.util.SerializationUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {
	
	public static void main(String[] args) throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QueueName.getQueue(), true, false, false, null);
		String message = "";
		int delay = 0;

		
		if (args.length > 0 && args[0] instanceof String)
			message = args[0].toString();
		
		if (args.length > 1 )
			try{
				delay = Integer.parseInt(args[1].toString());
			} catch (Exception e){
				System.out.println("Can't parse the integer: \n" + e.getMessage());
			}
		
		
		Message msg = new Message();
		
		msg.setDelay(delay);
		msg.setMsg(message);
		
		
		// Persistant messages
		
		channel.basicPublish("", QueueName.getQueue(), MessageProperties.PERSISTENT_TEXT_PLAIN, SerializationUtils.serialize(msg));
		
		
		System.out.println(" [x] Sent '" + msg.getMsg() + "', delay: " + msg.getDelay());
		
		channel.close();
		connection.close();
	}
	

}
