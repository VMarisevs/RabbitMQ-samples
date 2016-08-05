### Rabbit MQ sample

executing Maven project from command line with (MojoHaus)[http://www.mojohaus.org/exec-maven-plugin/java-mojo.html]



Before running these commands, load the RabbitMQ Server:
```
	$ cd rabbitmq_server-*.*.*\sbin
	$ rabbitmq-server start
```


Samples from (RabbitMQ website)[https://www.rabbitmq.com/tutorials/tutorial-one-java.html]:
```
	$ mvn exec:java -Dexec.mainClass="com.rabbitmq.sample1.Receive"
	$ mvn exec:java -Dexec.mainClass="com.rabbitmq.sample1.Send"
```

```
	$ mvn exec:java -Dexec.mainClass="com.rabbitmq.sample2.Receive"
	$ mvn exec:java -Dexec.mainClass="com.rabbitmq.sample2.NewTask"
	$ mvn exec:java -Dexec.mainClass="com.rabbitmq.sample2.NewTask" -Dexec.arguments="Another message"
```


```
mvn exec:java -Dexec.mainClass="com.rabbitmq.sample2.Worker"
mvn exec:java -Dexec.mainClass="com.rabbitmq.sample2.NewTask" -Dexec.args="First 60000"
mvn exec:java -Dexec.mainClass="com.rabbitmq.sample2.NewTask" -Dexec.args="Second 1000"
mvn exec:java -Dexec.mainClass="com.rabbitmq.sample2.NewTask" -Dexec.args="Third 1000"
```
