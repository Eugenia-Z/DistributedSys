package Week2.Lab2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/* 
Key components:
1. BlockingQueue:a thread-safe queue that allows threads to produce and consume items safely
2. Executor Service:Manages a pool of worker threads for parallel processing
3. FileWriter:Used for writing data to the FileWriter

Thread design:
1. Main Thread
2. Worker Thread
3. Writer Thread 

Execution time: 
1000 strings: 332 ms
5000 strings: 1182 ms
10000 stirngs: 2383 ms

*/

public class FileWriter4 {
    // BlockingQueue to hold the generated strings
    private static final BlockingQueue<String> queue = new LinkedBlockingDeque<>();
    private static final int NUM_THREADS = 500;
    private static final int STRINGS_PER_THREAD = 10000;

    public static void main(String[] args) throws InterruptedException {
        // File to write the generated Strings
        File file = new File("output4.txt");

        // log the time
        long startTime = System.currentTimeMillis();

        // ExecutorService to manage worker threads
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS + 1);

        // start the writer thread
        executor.submit(new FileWriterTask(file));

        // Create and start worker threads
        for (int i = 0; i < NUM_THREADS; i++) {
            final int threadId = i;
            executor.submit(() -> generateStrings(threadId));
        }

        // Shutdown the executor and wait for all threads to finish
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // Record the end time
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
    }

    // Method to generate strings and put them in the queue
    private static void generateStrings(int threadId) {
        for (int i = 0; i < STRINGS_PER_THREAD; i++) {
            String timestamp = String.valueOf(System.currentTimeMillis());
            String message = timestamp + ", " + threadId + ", " + i;

            try {
                queue.put(message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Task for the writer thread to write to the file
    static class FileWriterTask implements Runnable {
        private final File file;

        public FileWriterTask(File file) {
            this.file = file;
        }

        @Override
        public void run() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                while (true) {
                    String message = queue.take(); // Take a message from the queue
                    writer.write(message);
                    writer.newLine();

                    // Stop if all threads are finished
                    if (queue.isEmpty() && Thread.activeCount() == 2) {
                        break;
                    }
                }
            } catch (IOException | InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}