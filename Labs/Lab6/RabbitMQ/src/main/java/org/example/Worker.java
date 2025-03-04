package org.example;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class Worker {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.basicQos(1); // accept only one unack-ed message at a time

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

            System.out.println(" [x] Received '" + message + "'");
            try {
                doWork(message);
            } finally {
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> { });
    }

    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}

/*
output:
 [*] Waiting for messages. To exit press CTRL+C
 [x] Received ''
 [x] Done
 [x] Received 'Hello RabbitMQ'
 [x] Done
RabbitMQ 的消息传递是异步的，如果消费者的 basicConsume() 方法被调用并且此时队列为空，那么有时消费者的处理逻辑可能会接收到一个空字符串。
这通常是因为某些特定的队列配置（例如 autoAck 设置），或者消费队列中的第一个空白消息。

Round-robin dispatching:
- By default, RabbitMQ will send each message to the next consumer, in sequence.

Acknowledgement:
- Acks must be sent on the same channel that received the delivery. Attempt to acknowledge using a different channel will result in a channel-level protocol exception.

Fair Dispatching:
- use channel.basicQos(prefetchCount=1);
- tells RabbitMQ not to give more than one message to a worker at a time.

 */
