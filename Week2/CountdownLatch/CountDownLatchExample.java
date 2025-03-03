package Week2.CountdownLatch;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {

    public static void main(String[] args) throws InterruptedException {
        // The latch will wait for 3 worker threads to finish
        CountDownLatch latch = new CountDownLatch(3);

        // Create and start 3 worker threads
        for (int i = 0; i < 3; i++) {
            final int workerId = i + 1;
            new Thread(() -> {
                try {
                    System.out.println("Worker " + workerId + " is working...");
                    Thread.sleep((long) (Math.random() * 1000)); // Simulate work
                    System.out.println("Worker " + workerId + " has finished.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown(); // Decrement the count when a worker is done
                }
            }).start();
        }

        // Main thread waits for the workers to finish
        System.out.println("Main thread is waiting for workers to finish...");
        latch.await(); // Main thread will wait/block until latch count reaches zero
        System.out.println("All workers have finished. Main thread can now proceed.");
    }
}

// line 28 will print out first.
// 1. new Thread(...).start(); 只是启动线程，不会阻塞 main 线程。主线程不会等待 worker
// 线程启动完成，而是立即继续执行。
// 2. line 28 executes before latch.await();
// latche.await()只会在主线程执行到这一行时阻塞，等待worker线程完成工作。

/*
 * 为什么大概率 Main thread is waiting for workers to finish...先被打印？
 * 1. 主线程是顺序执行的，而 worker 线程是异步执行的：
 * 
 * new Thread(...).start();不会阻塞主线程，主线程会立即执行
 * - System.out.println("Main thread is waiting for workers to finish...");。
 * - Worker 线程需要排队等待CPU 调度，可能会稍有延迟。
 * 
 * Worker 线程的 System.out.println("Worker X is working...")可能会比主线程稍晚执行：
 * 
 * - start() 只是让线程进入“就绪状态”，它何时真正执行取决于 CPU 线程调度。
 * - 主线程是已经运行的线程，它的 println 可能会在 worker 线程被调度前先执行。
 */