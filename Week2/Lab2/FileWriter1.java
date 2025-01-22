package Week2.Lab2;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.io.IOException;

/* Approach: 
/* Store the generated String in a shared data structure;
/* Perform I/O once only in the main thread
/* Execution time: 
/* 1000 strings: 257 ms
/* 5000 strings: 872 ms
/* 10000 stirngs: 1558 ms
 */

public class FileWriter1 {
    private static final int THREAD_COUNT = 500;
    private static final int STRINGS_PER_THREAD = 10000;
    private static final String FILE_PATH = "output1.txt";

    // crate new thread in main function using lambda
    public static void main(String[] args) {

        // log the start time
        long startTime = System.currentTimeMillis();

        // Shared sychronized data structure
        List<String> sharedData = new ArrayList<>();
        Object lock = new Object();

        // Latch to wait for all threads to finish
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        // Create and start new thread
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(() -> {
                try {
                    List<String> localData = new ArrayList<>();
                    for (int j = 0; j < STRINGS_PER_THREAD; j++) {
                        long timestamp = System.currentTimeMillis();
                        long threadId = Thread.currentThread().getId();
                        localData.add(timestamp + ", " + threadId + ", " + j);
                    }
                    synchronized (lock) {
                        sharedData.addAll(localData);
                    }
                } finally {
                    latch.countDown();
                }
            });
            thread.start();
        }

        // Wait for all threads to complete
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Write to file in the main thread
        // remove the confusion because this class is called FileWriter too
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(FILE_PATH))) {
            for (String line : sharedData) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // End time measurement
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
    }
}
