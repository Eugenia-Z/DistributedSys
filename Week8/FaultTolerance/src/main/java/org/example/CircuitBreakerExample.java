package org.example;

import io.github.resilience4j.circuitbreaker.*;
import java.util.function.Supplier;

/*
Circuit Breaker Pattern prevents cascading failures by opening the circuit when failures exceed a threshold
故障隔离：
熔断器模式用于防止系统在调用故障时持续重试，导致整个系统崩溃。当故障发生过多时，熔断器会打开（Open），短时间内阻止请求，等待服务恢复后再尝试重新连接。

工作原理：
Closed（关闭）：正常工作状态，允许请求通过。如果失败率超过阈值，进入 Open 状态。
Open（打开）：熔断状态，直接拒绝请求，防止系统被过载。经过一定时间后进入 Half-Open。
Half-Open（半开）：尝试允许部分请求通过。如果恢复正常，则回到 Closed，否则继续 Open。

适用场景：
调用外部 API 时，避免长时间等待失败响应。
处理数据库故障，防止大量重试导致服务瘫痪。
微服务架构下，防止某个服务挂掉后影响整个系统。
 */
public class CircuitBreakerExample {
    public static void main(String[] args) {
        // Configure the circuit breaker
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)  // Open circuit if failure rate is >= 50%
                .slidingWindowSize(5)      // Look at the last 5 calls
                .minimumNumberOfCalls(3)   // Start tracking after 3 calls
                .waitDurationInOpenState(java.time.Duration.ofSeconds(5)) // Recovery time
                .build();

        CircuitBreaker circuitBreaker = CircuitBreaker.of("serviceCircuitBreaker", config);

        Supplier<String> supplier = CircuitBreaker.decorateSupplier(circuitBreaker, () -> {
            if (Math.random() > 0.5) { // 50% probability to simulate failure
                throw new RuntimeException("Service failed!");
            }
            return "Success!";
        });

        for (int i = 0; i < 10; i++) {
            try {
                System.out.println("Call " + (i + 1) + ": " + supplier.get());
            } catch (Exception e) {
                System.out.println("Call " + (i + 1) + " failed: " + e.getMessage());
            }
        }
    }
}

/*
1. Supplier<String>：

Supplier 是 Java 8 引入的 函数式接口，它的 get() 方法会返回一个 String 值。
这里的 supplier.get() 执行时，要么返回 "Success!"，要么抛出异常 "Service failed!"。

2. CircuitBreaker.decorateSupplier(circuitBreaker, lambda expression):
    - decorateSupplier()创建一个受Circuit Breaker保护的supplier，
    - 当服务调用失败过多时，CircuitBreaker会开启，阻止新的调用，返回短路错误
    - when CircuitBreaker closed，正常执行supplier.get()方法
    - when CircuitBreaker half-open, 允许一部分请求通过以测试服务是否恢复

3. 适用于：
    - 调用外部API，避免长时间故障导致系统崩溃
    - 数据库查询，防止过载
    - 微服务调用，防止雪崩效应
 */
