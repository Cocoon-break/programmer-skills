# RabbitMQ的JAVA基础操作

---

一、相关准备
        1. 本例子使用Maven作为构建工具，请使用者自行安装Maven，相关Maven的教程会在之后补充，尽请期待。
        2. 在Maven的pom.xml中添加如下内容，导入rabbitMq和序列化的相关Jar包。
                `<dependency>
                <groupId>com.rabbitmq</groupId>
                <artifactId>amqp-client</artifactId>
                <version>3.0.4</version>
                </dependency>`
                `<dependency>
                <groupId>commons-lang</groupId>
                 <artifactId>commons-lang</artifactId>
                 <version>2.6</version>
                </dependency>`
        3. 确保RabbitMQ已经安装完毕，便于测试。
二、相关代码
        1、建立与RabbitMQ的基础连接
                MQ的连接分为两个角色，producer和consumer。他们的连接可以抽象成一个类进行描述。
                [BaseConnector](https://github.com/Cocoon-break/programmer-skills/blob/master/RabbitMQ/src/main/java/connector/BaseConnector.java)
                之后我们分别定义producer和consumer两个类。
                [MqConsumer](https://github.com/Cocoon-break/programmer-skills/blob/master/RabbitMQ/src/main/java/connector/MqConsumer.java)
                [MqProducer](https://github.com/Cocoon-break/programmer-skills/blob/master/RabbitMQ/src/main/java/connector/MqProducer.java)
        2.定义需要传输的消息类型
                为了让我们在Mq中进行消息的生产和消费，我们需要定义一个消息的类，并且需要对其进行序列化。
                [MessageData](https://github.com/Cocoon-break/programmer-skills/blob/master/RabbitMQ/src/main/java/messageEntity/MessageData.java)
        3.进行相关测试
                编写Main，对代码进行测试
                [Test](https://github.com/Cocoon-break/programmer-skills/blob/master/RabbitMQ/src/main/java/mainTest/Test.java)
                注意：这里的多线程，我使用了线程池对其进行控制，免去了手动控制的繁琐。
                `ExecutorService executorService= Executors.newCachedThreadPool();`
                关于相关线程池的教程，今后会继续详细补充。大家多多支持，多多期待。





