package Week2.Lab2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/* Approach: 
/* Perform I/O once per thread (before each thread terminates)
/* use a shared fileWriter
/* Execution time: 
/* 1000 strings: 111 ms
/* 5000 strings: 315 ms
/* 10000 stirngs: 783 ms
 */

public class FileWriter2 {
    private static final int THREAD_COUNT = 500;
    private static final int STRINGS_PER_THREAD = 5000;
    private static final String FILE_PATH = "output2.txt";

    public static void main(String[] args) {
        // log the start time
        long startTime = System.currentTimeMillis();

        // Create a shared file writer
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(FILE_PATH))) {

            // Create latch to wait for all threads to finish
            CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

            // Create and start threads
            for (int i = 0; i < THREAD_COUNT; i++) {
                Thread thread = new Thread(() -> {
                    try {
                        StringBuilder sb = new StringBuilder();
                        for (int j = 0; j < STRINGS_PER_THREAD; j++) {
                            long timeStamp = System.currentTimeMillis();
                            long threadId = Thread.currentThread().getId();
                            sb.append(timeStamp).append(",").append(threadId).append(",").append(j).append("\n");
                        }
                        synchronized (writer) {
                            writer.write(sb.toString());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                    }
                });
                thread.start();
            }

            // wait for all threads to complete
            latch.await();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // log the end time
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
    }
}
