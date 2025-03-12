package org.example;

import io.github.resilience4j.bulkhead.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/*
Limits the number of concurrent requests to prevent a single service from overloading the entire system
舱壁模式用于隔离不同部分的资源，防止某个功能的过载影响整个系统。

工作原理：
线程池隔离（ThreadPool Bulkhead）：不同任务分配不同线程池，防止一个任务占用所有资源。
并发请求限制（Semaphore Bulkhead）：限制某个功能的并发访问量，超过限制的请求会被拒绝或排队。

适用场景：
限制高并发 API 请求，防止某个 API 影响其他 API。
防止数据库连接池耗尽，不同查询使用不同的连接池。
在微服务架构 中，确保某个服务的资源不会被某个高流量功能独占。

 */

public class BulkheadExample {
    public static void main(String[] args) {
        // 创建 Bulkhead 配置（最大并发请求数=2）
        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(2)  // 限制最多 2 个任务同时执行
                .build();
        BulkheadRegistry registry = BulkheadRegistry.of(config);

        // create the Bulkhead
        Bulkhead bulkhead = registry.bulkhead("myBulkhead");

        // Create a supplier for originalTask() method
        Supplier<String> originalTask = () -> {
            try {
                Thread.sleep(1000); // 模拟耗时任务
                return "Task Completed";
            } catch (InterruptedException e) {
                return "Task Interrupted";
            }
        };

        // Decorate originalTask() with the bulkhead config
        Supplier<String> decoratedTask = Bulkhead.decorateSupplier(bulkhead, originalTask);

        // execute tasks using a thread pool
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executor.submit(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " - " + decoratedTask.get());
                } catch (Exception e) {
                    System.out.println(Thread.currentThread().getName() + " - " + e.getMessage());
                }
            });
        }

        executor.shutdown();
    }
}
