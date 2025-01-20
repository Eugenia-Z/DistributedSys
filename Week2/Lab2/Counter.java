package Week2.Lab2;

public class Counter {
    private static final int NUM_THREADS = 1000000;
    private static final int INCREMENT_PER_THREAD = 10;
    private static int counter = 0;

    private static synchronized void incrementCounter() {
        counter++;
    }

    static class WorkerThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < INCREMENT_PER_THREAD; i++) {
                incrementCounter();
            }
        }
    }

    public static void main(String[] args) {

        // log the start time
        long startTime = System.currentTimeMillis();

        // create and start threads
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new WorkerThread();
            threads[i].start();
        }

        // wait for all threads to complete
        try {
            for (int i = 0; i < NUM_THREADS; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // log the end time
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // print result
        System.out.println("Final counter value: " + counter);
        System.out.println("Expected counter value: " + NUM_THREADS * INCREMENT_PER_THREAD);
        System.out.println("Duration: " + duration + " milliseconds.");
    }
}

/*
 * public class Counter {
 * 
 * // Shared synchronized counter
 * private static final Object lock = new Object();
 * private static int counter = 0;
 * 
 * // Method to increment the counter
 * public static void incrementCounter() {
 * synchronized (lock) {
 * counter++;
 * }
 * }
 * 
 * public static void main(String[] args) throws InterruptedException {
 * final int NUM_THREADS = 1000000; // Default number of threads
 * final int INCREMENTS_PER_THREAD = 10;
 * 
 * // Start timestamp
 * long startTime = System.currentTimeMillis();
 * 
 * // Array to hold threads
 * Thread[] threads = new Thread[NUM_THREADS];
 * 
 * // Create and start threads
 * for (int i = 0; i < NUM_THREADS; i++) {
 * threads[i] = new Thread(() -> {
 * for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
 * incrementCounter();
 * }
 * });
 * threads[i].start();
 * }
 * 
 * // Wait for all threads to complete
 * for (Thread thread : threads) {
 * thread.join();
 * }
 * 
 * // End timestamp
 * long endTime = System.currentTimeMillis();
 * 
 * // Print results
 * System.out.println("Final Counter Value: " + counter);
 * System.out.println("Expected counter value: " + NUM_THREADS *
 * INCREMENTS_PER_THREAD);
 * System.out.println("Duration: " + (endTime - startTime) + " ms");
 * }
 * }
 */

// Insights:
// # Thread = 1: 1ms vs. 3ms
// # Thread = 10: 0ms vs. 6ms
// # Thread = 100: 3ms vs. 7ms
// # Thread = 1000: 22ms vs. 27ms
// # Thread = 10000: 202ms vs. 228ms
// # Thread = 100000: 2041ms vs. 2000ms
// # Thread = 1000000: 19961ms vs. 19996 ms