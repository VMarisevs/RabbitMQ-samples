### Rabbit MQ sample

executing Maven project from command line with (MojoHaus)[http://www.mojohaus.org/exec-maven-plugin/java-mojo.html]



Before running these commands, load the RabbitMQ Server:
```

	$ cd rabbitmq_server-*.*.*\sbin
	$ rabbitmq-server start
```


```

	$ rabbitmqctl list_exchanges
	$ rabbitmqctl list_queues name messages_ready messages_unacknowledged
```


Samples from (RabbitMQ website)[https://www.rabbitmq.com/tutorials/tutorial-one-java.html]:


## Sample 1
```

	$ mvn exec:java -Dexec.mainClass="com.rabbitmq.sample1.Receive"
	$ mvn exec:java -Dexec.mainClass="com.rabbitmq.sample1.Send"
```


## Sample 2
```

	$ mvn exec:java -Dexec.mainClass="com.rabbitmq.sample2.Receive"
	$ mvn exec:java -Dexec.mainClass="com.rabbitmq.sample2.NewTask"
	$ mvn exec:java -Dexec.mainClass="com.rabbitmq.sample2.NewTask" -Dexec.arguments="Another message"
```


```

	$ mvn exec:java -Dexec.mainClass="com.rabbitmq.sample2.Worker"
	$ mvn exec:java -Dexec.mainClass="com.rabbitmq.sample2.NewTask" -Dexec.args="First 60000"	
	$ mvn exec:java -Dexec.mainClass="com.rabbitmq.sample2.NewTask" -Dexec.args="Second 1000"
	$ mvn exec:java -Dexec.mainClass="com.rabbitmq.sample2.NewTask" -Dexec.args="Third 1000"
```


## Sample 3
```

	mvn exec:java -Dexec.mainClass="com.rabbitmq.sample3.ReceiveLogs"
	mvn exec:java -Dexec.mainClass="com.rabbitmq.sample3.EmitLog"
```

## Sample 4


```

	mvn exec:java -Dexec.mainClass="com.rabbitmq.sample4.ReceiveLogsDirect" -Dexec.args="info warning" > ./target/logs_from_rabbit.log
	mvn exec:java -Dexec.mainClass="com.rabbitmq.sample4.ReceiveLogsDirect" -Dexec.args="info warning error"
	mvn exec:java -Dexec.mainClass="com.rabbitmq.sample4.EmitLogDirect"  -Dexec.args="error 'Run. Run. Or it will explode.'"
```
