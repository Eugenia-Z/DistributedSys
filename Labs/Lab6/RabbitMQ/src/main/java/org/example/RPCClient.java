package org.example;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author eugenia
 * @date 3/5/25
 */
public class RPCClient implements AutoCloseable{
    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";

    public RPCClient() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
    }
    public static void main(String[] args) throws IOException, TimeoutException {
        try{
            RPCClient fibanacciRpc = new RPCClient();
            for (int i = 0; i < 32; i++) {
                String i_str = Integer.toString(i);
                System.out.println(" [x] Requesting fib(" + i_str + ")");
                String response = fibanacciRpc.call(i_str);
                System.out.println(" [.] Received response: " + response);
            }
        } catch (IOException | TimeoutException | InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
    }

    /**
     * The message itself is the string version of function parameter to be passed into fib().
     * @param message
     * @return response in String format
     * @throws IOException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public String call(String message) throws IOException, InterruptedException, ExecutionException {
        final String corrID = UUID.randomUUID().toString();
        String replyQueueName = channel.queueDeclare().getQueue(); // create a dedicated queue for the reply
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrID) // consumer callback will use this value to match the appropriate response
                .replyTo(replyQueueName) // subscribe to the reply queue.
                .build();

        channel.basicPublish("", requestQueueName, props, message.getBytes(StandardCharsets.UTF_8));

        // block the main thread till the response arrives. (Main is waiting for the completableFuture)
        // 存储并等待服务器的相应，CompletableFuture 允许在异步操作完成后自动触发回调。
        final CompletableFuture<String> response = new CompletableFuture<>();
        String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
            if(delivery.getProperties().getCorrelationId().equals(corrID)){
                response.complete(new String(delivery.getBody(), StandardCharsets.UTF_8));
            }
        }, consumerTag -> {});

        // block main thread, 直到服务器返回结果
        String result = response.get();

        // 一旦收到响应，调用basicCancel取消监听，以节省资源。
        channel.basicCancel(ctag);

        return result;
    }


    @Override
    public void close() throws Exception {
        connection.close();
    }
}

/*
1. Client端有一个basicConsume是用来监听replyQueueName队列，接收服务器的响应。
2. 第二个回调 (consumerTag -> {}) 用于处理消费取消的情况（这里未具体实现）。

ctag
1. consumer tag, 用于标识 RabbitMQ 中的一个 消息消费者。它在 basicConsume() 方法调用时由 RabbitMQ 返回，并可以用于取消订阅。
 */