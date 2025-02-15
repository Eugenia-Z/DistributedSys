# RabbitMQ

RabbitMQ is an open-source message broker that enables applications to communicate with each other asynchronously. It supports multiple messaging protocols and can be used to implement distributed systems, decoupled microservices, and task queues.

## Key Concepts 主要概念

### Producer (生产者)

The sender of messages. It sends messages to the broker (RabbitMQ).
生产者是消息的发送方，它将消息发送到 RabbitMQ。

### Queue (队列)

A buffer that stores messages until they are processed.
队列用于存储消息，直到它们被处理。

### Consumer (消费者)

The receiver of messages. It retrieves messages from the queue and processes them.
消费者是接收消息的组件，从队列中获取消息并进行处理。

### Exchange (交换机)

A routing mechanism that determines which queue(s) a message should go to.
交换机负责路由消息，决定消息应该被发送到哪个队列。

### Binding (绑定)

The link between an exchange and a queue.
绑定是交换机和队列之间的连接。

### Routing Key (路由键)

A key used to route messages to the correct queue(s).
用于将消息路由到正确队列的关键字。

### Acknowledgment (消息确认)

A mechanism to ensure messages are processed successfully before removal from the queue.
确认机制确保消息在成功处理之前不会从队列中删除。

# Types of Exchanges 交换机类型

## Direct Exchange (直连交换机)

Messages are routed based on an exact match of the routing key.
消息根据完全匹配的路由键发送到特定队列。

## Fanout Exchange (扇出交换机)

Messages are broadcast to all bound queues.
消息会广播到所有绑定的队列。

## Topic Exchange (主题交换机)

Messages are routed based on pattern matching with wildcards (\*, #).
通过通配符匹配路由键，实现消息的分发。

## Headers Exchange (头交换机)

Uses message headers instead of routing keys for routing decisions.
根据消息头信息进行路由，而不是路由键。

# WorkFlow

1. Producer sends a message to an exchange.

2. Exchange routes the message to the appropriate queue(s) based on routing rules.

3. Consumer retrieves and processes the message from the queue.

4. Consumer sends an acknowledgment to RabbitMQ.

# Producer creates Exchange, Consumer bind queues to exchange

通常在消息队列的模型中，生产者（producer）会创建 exchange，而 消费者（consumer）会将自己的 queue 与 exchange 绑定。

简要说明：
Producer 创建 Exchange：

生产者 负责向 exchange 发送消息，但生产者不会直接操作队列。
Exchange 负责根据路由规则将消息转发到一个或多个绑定的队列。
Exchange 可以是多种类型，比如 direct, topic, fanout, 或 headers，每种类型的路由规则不同。
例如：

```java
channel.exchangeDeclare(EXCHANGE_NAME, "direct");
channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
```

Consumer Bind to Queue：

消费者 绑定到 队列，并监听消息。
消费者会从已经存在的队列中获取消息，处理后进行确认（acknowledgment）。
队列与 exchange 通过 binding 进行关联，消息从 exchange 被路由到绑定的队列。
例如：

```java
channel.queueDeclare(QUEUE_NAME, true, false, false, null);
channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, routingKey);
channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
```

总结：
Producer 创建 Exchange 并发布消息。
Consumer 绑定到 Queue，并从队列中接收消息。

# Roles of Channel

在 RabbitMQ 中，channel 是连接（connection）和消息传递过程之间的一个重要组件。可以将 channel 看作是一个虚拟的 通信通道，它用于进行消息的发送、接收和处理。一个 连接（connection） 可以创建多个 channel，每个 channel 处理不同的消息传递任务。

主要功能和作用：
消息发送（Publish）：

生产者通过 channel 向 exchange 发布消息。channel 是生产者和 RabbitMQ 之间的主要通信接口。
例如，使用 channel.basicPublish() 发送消息。

```java
channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
```

消息接收（Consume）：

消费者通过 channel 从队列中获取并处理消息。channel 负责接收队列中的消息，并触发相应的回调来处理消息。
例如，使用 channel.basicConsume() 来开始消费消息。

```java
channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
```

队列声明和绑定：

channel 用于声明队列、交换机、绑定等操作。消费者通过 channel 绑定队列到交换机，确保消息能够正确路由。

```java
channel.queueDeclare(QUEUE_NAME, true, false, false, null);
channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, routingKey);
```

确认机制（Acknowledgments）：

channel 用于处理消息的确认（ack）和拒绝（nack）。消费者处理完消息后，需要通过 channel.basicAck() 或 channel.basicReject() 来确认消息已经处理。
例如，消费者处理消息并发送确认。

```java
channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
```

处理错误和异常：
channel 还用于处理与消息传递过程中的错误和异常，进行消息重试、死信队列等操作。

总结：

channel 是 RabbitMQ 中的一个操作接口，用于发送和接收消息。
它管理消息的发布、消费、队列声明、交换机声明、绑定操作以及消息确认等。
一个连接可以拥有多个 channel，并且 channel 是轻量级的、线程不安全的，因此通常每个线程使用一个独立的 channel。
在实际应用中，为了保证并发和效率，通常一个消费者会为每个线程或者每个任务创建一个单独的 channel。
