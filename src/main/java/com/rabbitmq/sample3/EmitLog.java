package com.rabbitmq.sample3;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLog {

	public static void main(String[] args) throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(ExchangeName.EXCHANGE_NAME, "fanout");

        String message = getMessage(args);

        channel.basicPublish(ExchangeName.EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
	}
	
	private static String getMessage(String[] strings){
	    if (strings.length < 1)
	        return "Hello World!";
	    return joinStrings(strings, " ");
	}

	private static String joinStrings(String[] strings, String delimiter) {
	    int length = strings.length;
	    if (length == 0) return "";
	    StringBuilder words = new StringBuilder(strings[0]);
	    for (int i = 1; i < length; i++) {
	        words.append(delimiter).append(strings[i]);
	    }
	    return words.toString();
	}
}
