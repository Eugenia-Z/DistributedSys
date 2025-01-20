package Week2.Lab2;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Collections2 {
    private static final int NUM_ELEMENTS = 100000;
    private static final int NUM_THREADS = 100;
    private static final int ELEMENTS_PER_THREAD = NUM_ELEMENTS / NUM_THREADS;

    static class Worker implements Runnable {
        private final Map<Integer, String> map;
        private final AtomicInteger counter;

        public Worker(Map<Integer, String> map, AtomicInteger counter) {
            this.map = map;
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < ELEMENTS_PER_THREAD; i++) {
                int key = counter.getAndIncrement();
                map.put(key, "Value " + key);
            }
        }
    }

    private static void runTest(Map<Integer, String> map, String mapType) throws InterruptedException {
        System.out.println("Testing  " + mapType);
        Thread[] threads = new Thread[NUM_THREADS];
        AtomicInteger counter = new AtomicInteger();

        // Start timing
        long startTime = System.currentTimeMillis();

        // create and start threads
        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(new Worker(map, counter));
            threads[i].start();
            ;
        }

        // wait for completion
        for (Thread thread : threads) {
            thread.join();
        }
        // End timing
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("Time taken: " + duration + " ms");
        System.out.println("Final size: " + map.size() + "\n");
    }

    public static void main(String[] args) throws InterruptedException {
        // Single-threaded tests
        System.out.println("Single-threaded tests:");

        // Test HashTable
        Hashtable<Integer, Integer> hashTable = new Hashtable<>();
        long hashTableStartTime = System.currentTimeMillis();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            hashTable.put(i, i);
        }
        long hashTableEndTime = System.currentTimeMillis();
        long hashTableDuration = (hashTableEndTime - hashTableStartTime);

        // Test HashMap
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        Map<Integer, Integer> synchronizedHashMap = Collections.synchronizedMap(hashMap);
        long hashMapStartTime = System.currentTimeMillis();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            synchronizedHashMap.put(i, i);
        }
        long hashMapEndTime = System.currentTimeMillis();
        long hashMapDuration = (hashMapEndTime - hashMapStartTime);

        // Test ConcurrentHashMap
        ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        long concurrentHashMapStartTime = System.currentTimeMillis();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            concurrentHashMap.put(i, i);
        }
        long concurrentHashMapleEndTime = System.currentTimeMillis();
        long concurrentHashMapleDuration = (concurrentHashMapleEndTime - concurrentHashMapStartTime);

        System.out.println("=== Collection Performance Benchmark ===");
        System.out.println("Number of elements: " + NUM_ELEMENTS);

        System.out.println("\nHashTable:");
        System.out.println("Time taken: " + hashTableDuration + " ms");
        System.out.println("Final size: " + hashTable.size());

        System.out.println("\nHashMap:");
        System.out.println("Time taken: " + hashMapDuration + " ms");
        System.out.println("Final size: " + hashMap.size());

        System.out.println("\nconcurrentHashMap(synchronized):");
        System.out.println("Time taken: " + concurrentHashMapleDuration + " ms");
        System.out.println("Final size: " + concurrentHashMap.size());

        // Multi-threaded tests
        System.out.println("\nMulti-threaded tests:");
        runTest(new Hashtable<>(), "Hashtable");
        runTest(Collections.synchronizedMap(new HashMap<>()), "Synchronized HashMap");
        runTest(new ConcurrentHashMap<>(), "ConcurrentHashMap");
    }
}
