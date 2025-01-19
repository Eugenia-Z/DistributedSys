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
