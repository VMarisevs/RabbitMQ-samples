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


## Sample 1 "Hello World!"
The simplest thing that does something

```

	$ mvn exec:java -Dexec.mainClass="com.rabbitmq.sample1.Receive"
	$ mvn exec:java -Dexec.mainClass="com.rabbitmq.sample1.Send"
```


## Sample 2 Work queues
Distributing tasks among workers

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


## Sample 3 Publish/Subscribe
Sending messages to many consumers at once

```

	mvn exec:java -Dexec.mainClass="com.rabbitmq.sample3.ReceiveLogs"
	mvn exec:java -Dexec.mainClass="com.rabbitmq.sample3.EmitLog"
```

## Sample 4 Routing
Receiving messages selectively

```

	mvn exec:java -Dexec.mainClass="com.rabbitmq.sample4.ReceiveLogsDirect" -Dexec.args="info warning" > ./target/logs_from_rabbit.log
	mvn exec:java -Dexec.mainClass="com.rabbitmq.sample4.ReceiveLogsDirect" -Dexec.args="info warning error"
	mvn exec:java -Dexec.mainClass="com.rabbitmq.sample4.EmitLogDirect"  -Dexec.args="error 'Run. Run. Or it will explode.'"
```


## Sample 5 Topics
Receiving messages based on a pattern

```
To receive all the logs:

	mvn exec:java -Dexec.mainClass="com.rabbitmq.sample5.ReceiveLogsTopic" -Dexec.args="#"
	mvn exec:java -Dexec.mainClass="com.rabbitmq.sample5.EmitLogTopic"  -Dexec.args="error 'Run. Run. Or it will explode.'"
	
To receive all logs from the facility "kern":
	
	mvn exec:java -Dexec.mainClass="com.rabbitmq.sample5.ReceiveLogsTopic" -Dexec.args="kern.*"
	mvn exec:java -Dexec.mainClass="com.rabbitmq.sample5.EmitLogTopic"  -Dexec.args="kern.binding 'Run. Run. Or it will explode.'"
	
You can create multiple bindings:

	mvn exec:java -Dexec.mainClass="com.rabbitmq.sample5.ReceiveLogsTopic" -Dexec.args="'kern.*' '*.critical'"
	mvn exec:java -Dexec.mainClass="com.rabbitmq.sample5.EmitLogTopic"  -Dexec.args="kern.critical 'A critical kernel error'"
```

## Sample 6 Remote procedure call (RPC)
Although RPC is a pretty common pattern in computing, it's often criticised. The problems arise when a programmer is not aware whether a function call is local or if it's a slow RPC. Confusions like that result in an unpredictable system and adds unnecessary complexity to debugging. Instead of simplifying software, misused RPC can result in unmaintainable spaghetti code.

Bearing that in mind, consider the following advice:

Make sure it's obvious which function call is local and which is remote.
Document your system. Make the dependencies between components clear.
Handle error cases. How should the client react when the RPC server is down for a long time?
When in doubt avoid RPC. If you can, you should use an asynchronous pipeline - instead of RPC-like blocking, results are asynchronously pushed to a next computation stage.

```

	mvn exec:java -Dexec.mainClass="com.rabbitmq.sample6.RPCServer"
	mvn exec:java -Dexec.mainClass="com.rabbitmq.sample6.RPCClient" -Dexec.args="30"
```

