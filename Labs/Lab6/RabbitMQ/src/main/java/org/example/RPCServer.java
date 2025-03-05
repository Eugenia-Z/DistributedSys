package org.example;

import com.rabbitmq.client.*;

import java.awt.print.PrinterGraphics;
import java.nio.charset.StandardCharsets;

/**
 * @author eugenia
 * @date 3/5/25
 */
public class RPCServer {
    private static final String RPC_QUEUE_NAME = "rpc_queue";

    private static int fib(int n){
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n - 1) + fib(n - 2);
    }

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
        channel.queuePurge(RPC_QUEUE_NAME);

        channel.basicQos(1);
        System.out.println("Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(delivery.getProperties().getCorrelationId())
                    .build();
            String response = "";
            try{
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                int n = Integer.parseInt(message);

                System.out.println(" [.] fib(" + message + ")");
                response += fib(n);
            } catch (RuntimeException e){
                System.out.println(" [.] " + e.getMessage());
            } finally {
                channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes(StandardCharsets.UTF_8));
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(RPC_QUEUE_NAME, false, deliverCallback, consumerTag -> {});
        // use basicConsume to access the queue, where we provide a callback in the form of an object (DeliveryCallback) that will do the work and send the response back.
    }
}

/*
Further Thinking:
1. How should the client react if there are no servers running?
    - 如果服务器不可用，basicPublish()会抛出异常（IOException），客户端可以捕获异常并给出错误信息
    - Client retry：exponential backoff
    - server failover, retry with other servers or load balancing strategy
    - clear error msg: "RPC service unavailable, please try later."

2. RPC 调用是否应该有超时机制？
    - CompletableFuture<String> response = new CompletableFuture<>(); 默认情况下会无限等待。
    - 使用 response.get(timeout, TimeUnit.SECONDS); 限制等待时间：
        try {
            String result = response.get(5, TimeUnit.SECONDS); // 5秒超时
        } catch (TimeoutException e) {
            throw new RuntimeException("RPC 请求超时");
        }
3. 服务器发生异常，是否应该转发给客户端？
   If the server malfunctions and raises an exception, should it be forwarded to the client?

   直接转发（不推荐）：如果服务器异常直接作为响应返回，可能会暴露服务器的内部逻辑或安全漏洞
   标准错误格式（推荐）：服务器应返回 JSON 格式的标准错误信息 {"error": "Invalid request", "code": 400}

4. 服务器应该在处理请求前进行输入验证，以避免异常或恶意请求
   Protecting against invalid incoming messages (eg checking bounds, type) before processing.

   - 数据类型检查：确保 message 是符合预期格式的字符串或 JSON。
   - 边界检查：防止 message 过大（如超出最大字符数）或内容不合法。
   - 异常处理：
    if (message == null || message.isEmpty()) {
       throw new IllegalArgumentException("请求不能为空");
    }
 */