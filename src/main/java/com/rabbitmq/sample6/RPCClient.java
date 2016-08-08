package com.rabbitmq.sample6;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class RPCClient {

	private Connection connection;
	private Channel channel;
	private String replyQueueName;
	private QueueingConsumer consumer;
	
	
	public RPCClient() throws IOException{
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    connection = factory.newConnection();
	    channel = connection.createChannel();

	    replyQueueName = channel.queueDeclare().getQueue(); 
	    consumer = new QueueingConsumer(channel);
	    channel.basicConsume(replyQueueName, true, consumer);
	}
	
	public void close() throws Exception {
	    connection.close();
	}
	
	public String call(String message) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException{
		String response = null;
		
		String corrId = java.util.UUID.randomUUID().toString();
		
		BasicProperties props = new BasicProperties
									.Builder()
									.correlationId(corrId)
									.replyTo(replyQueueName)
									.build();
		
		channel.basicPublish("", RPCServer.RPC_QUEUE_NAME, props, message.getBytes());
		
		while (true){
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			
			if (delivery.getProperties().getCorrelationId().equals(corrId)){
				response = new String(delivery.getBody());
				break;
			}
		}
		
		
		return response;
	}

	
	public static void main(String[] args) throws Exception {
		
		if (args.length < 1){
			System.err.println("[ERROR] Missing fibonacci argument.");
			System.exit(1);
		}
		
		RPCClient fibonacciRpc = new RPCClient();

		System.out.println(" [x] Requesting fib(" + args[0] +")");   
		String response = fibonacciRpc.call(args[0]);
		System.out.println(" [.] Got '" + response + "'");

		fibonacciRpc.close();
	}
}
