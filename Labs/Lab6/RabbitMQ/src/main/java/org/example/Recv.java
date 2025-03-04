package org.example;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {
    private final static String QUEUE_NAME = "hello";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});

    }
}

/*
1. DeliverCallback is used to buffer the messages pushed to us by the server
2. Why not use a try-with-resource statement to automatically close the channel & connection? -> because we want the process to stay alive while the consumer is listening asynchronously for messages to arrive.
3. Since the server pushes messages asynchronously, we use a callback in the form of na object that will buffer the message until we're ready to use them. (done in deliverCallback.)


- DeliverCallback is a Lambda expression, 相当于实现了DeliverCallback interface. 当队列有消息时，RabbitMQ会自动调用deliverCallback处理消息
- 参数consumerTag是消费者的唯一标识符，RMQ自动生成；delivery包含消息的元数据和消息体。
- delivery.getBody() captures the content of the message (byte[])
- new String(delivery.getBody(), "UTF-8") converts byte array to UTF-8 encoded String
- channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
- 当前通道channel监听RMQ queue_name队列，并使用 deliverCallback 处理收到的消息。
- true -> 自动应答，无需手动ack （if false，则需要 channel.basicAck() 确认消息-> channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);）
- deliverCallback: 当有消息时的回调函数，负责处理消息内容
- consumerTag -> {} : 取消监听的回调；此处空实现，表示不处理取消事件
 */