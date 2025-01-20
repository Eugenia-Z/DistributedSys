package Week2.Lab2;

import java.util.ArrayList;
import java.util.Vector;

public class Collections1 {
    private static final int NUM_ELEMENTS = 100000;

    public static void main(String[] args) {
        // Test vector
        Vector<Integer> vector = new Vector<>();
        long vectorStartTime = System.currentTimeMillis();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            vector.add(i);
        }
        long vectorEndTime = System.currentTimeMillis();
        long vectorDuration = (vectorEndTime - vectorStartTime);

        // Test ArrayList
        ArrayList<Integer> arrayList = new ArrayList<>();
        long arrayListStartTime = System.currentTimeMillis();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            arrayList.add(i);
        }
        long arrayListEndTime = System.currentTimeMillis();
        long arrayListDuration = (arrayListEndTime - arrayListStartTime);

        System.out.println("=== Collection Performance Benchmark ===");
        System.out.println("Number of elements: " + NUM_ELEMENTS);
        System.out.println("\nVector (synchronized):");
        System.out.println("Time taken: " + vectorDuration + " ms");
        System.out.println("Final size: " + vector.size());

        System.out.println("\nArrayList (not synchronized):");
        System.out.println("Time taken: " + arrayListDuration + " ms");
        System.out.println("Final size: " + arrayList.size());

        System.out.println("\nSynchronization overhead:");
        System.out.println("Difference: " + (vectorDuration - arrayListDuration) + " ms");
        double percentSlower = ((double) vectorDuration / arrayListDuration - 1) * 100;
        System.out.printf("Vector is %.1f%% slower than ArrayList%n", percentSlower);
    }
}
