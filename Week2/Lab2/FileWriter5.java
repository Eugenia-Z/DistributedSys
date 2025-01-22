package Week2.Lab2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/* 
Collect the strings first(in a thread-safe queue)
And then sort them before writing to the file.  
*/

/* 
Directly performing I/O immediately after generation in multiple threads 
does not guarantee the strings will be in ascending order.
 */

/* Execution time: 407ms 1000 strinds/thread */

public class FileWriter5 {
    // PriorityBlockQueue to hold the generated Strings in order of timestamp
    private static final PriorityBlockingQueue<StringData> queue = new PriorityBlockingQueue<>(500000,
            Comparator.comparingLong(StringData::getTimestamp));
    private static final int NUM_THREADS = 500;
    private static final int STRINGS_PER_THREAD = 1000;

    public static void main(String[] args) throws InterruptedException {
        File file = new File("output5.txt");
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        // Start the writer thread
        executor.submit(new FileWriterTask(file));

        // Create and start worker threads
        for (int i = 0; i < NUM_THREADS; i++) {
            final int threadId = i;
            executor.submit(() -> generateStrings(threadId));
        }

        // Shutdown the executor and wait for all the threads to finish
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // Record the end time
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + "ms");

    }

    // Method to generate strings and put them in the queue
    private static void generateStrings(int threadId) {
        for (int i = 0; i < STRINGS_PER_THREAD; i++) {
            long timestamp = System.currentTimeMillis();
            String message = timestamp + ", " + threadId + ", " + i;
            queue.put(new StringData(timestamp, message)); // Add the generated string to the queue
        }
    }

    // Task for the writer thread to write the file
    static class FileWriterTask implements Runnable {
        private final File file;

        public FileWriterTask(File file) {
            this.file = file;
        }

        @Override
        public void run() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                while (true) {
                    StringData data = queue.take();
                    writer.write(data.getMessage());
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

    // A helper class to store the string along with its timestamp
    static class StringData {
        private final long timeStamp;
        private final String message;

        public StringData(long timeStamp, String message) {
            this.timeStamp = timeStamp;
            this.message = message;
        }

        public long getTimestamp() {
            return timeStamp;
        }

        public String getMessage() {
            return message;
        }
    }
}

/*
 * awaitTermination() blocks the current thread (the thread calling it) until
 * either:
 * 1. All tasks in the executor have completed execution
 * 2. The timeout specified has elapsed
 */

/*
 * In a world parallel to this, there are always people live a life a version of
 * you could live.
 * But living an illusion like this won't make any sense. we gotta live in the
 * moment. 活在当下。
 */