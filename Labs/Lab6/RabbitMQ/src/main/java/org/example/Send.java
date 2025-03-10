package org.example;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            // Declaring a queue is idempotent - it will only be created if it doesn't exist already.
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello Kitty";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes()); // Message content is a byte array.
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}

/*
 * 1. Connection: abstracts the socket connection & takes care of protocol
 * version negotiation and authentication and so on for us.
 * 2. Both Connection and Channel implement Java.lang.AutoCloseable. this way we
 * don't need to close them explicitly in our code.
 * 3. To send, we then declare a queue for us to send to; then publish a message
 * to the queue. All done in try with resources statement.
 * 4. Declaring a queue is idempotent - it will only be created if it doesn't
 * exist already.
 */