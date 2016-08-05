package com.rabbitmq.sample2;

import java.io.IOException;
import java.util.Map;

import org.springframework.util.SerializationUtils;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Worker {
	
	public static void main(String[] args) throws IOException {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		
		final Connection connection = factory.newConnection();
		final Channel channel = connection.createChannel();
		
		channel.queueDeclare(QueueName.getQueue(), true, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		
		channel.basicQos(1);
		
		final Consumer consumer = new DefaultConsumer(channel){
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

			
				Message msg = (Message) SerializationUtils.deserialize(body);
		        System.out.println(" [x] Received '" + msg.getMsg() + "'; delay:" + msg.getDelay());
		        
		        try {
		          doWork(msg.getMsg(), msg.getDelay());
		        } finally {
		          System.out.println(" [x] Done");
		          channel.basicAck(envelope.getDeliveryTag(), false);
		        }
		      }
			
			
			
				private void doWork(String message, int delay) {
					try {
					  Thread.sleep(delay);
					} catch (InterruptedException _ignored) {
					  Thread.currentThread().interrupt();
					}				
					
				}
		    };
		    
		    channel.basicConsume(QueueName.getQueue(), false, consumer);
		}
	}

