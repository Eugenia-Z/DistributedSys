package Week2.CyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {
    public static void main(String[] args) {
        final int NUM_WORKERS = 3;

        /*
         * The second argument passed to the CyclicBarrier constructor is a Runnable,
         * which is the barrier action that will be executed once all threads have
         * reached the barrier.
         */
        CyclicBarrier barrier = new CyclicBarrier(NUM_WORKERS, () -> {
            System.out.println("All workers have reached the barrier. Proceeding to the next phase");
        }); // when all threads reach the barrier, line 16 is executed by the main thread

        Runnable workerTask = () -> {
            try {
                String workerName = Thread.currentThread().getName();
                System.out.println(workerName + " is working on phase 1...");
                Thread.sleep((long) (Math.random() * 1000));

                System.out.println(workerName + " reached the barrier.");
                barrier.await(); // wait for all workers to reach the barrier

                System.out.println(workerName + " is working on phase 2...");
                Thread.sleep((long) (Math.random() * 1000));

                System.out.println(workerName + " reached the barrier.");
                barrier.await(); // wait for all workers again # barrier can be reused for the second phase

                System.out.println(workerName + " is finishing work.");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        };
        for (int i = 0; i < NUM_WORKERS; i++) {
            new Thread(workerTask, "Worker " + (i + 1)).start();
        }
    }
}

// output:
/*
 * Worker 2 is working on phase 1...
 * Worker 3 is working on phase 1...
 * Worker 1 is working on phase 1...
 * Worker 1 reached the barrier.
 * Worker 3 reached the barrier.
 * Worker 2 reached the barrier.
 * All workers have reached the barrier. Proceeding to the next phase
 * Worker 2 is working on phase 2...
 * Worker 1 is working on phase 2...
 * Worker 3 is working on phase 2...
 * Worker 1 reached the barrier.
 * Worker 3 reached the barrier.
 * Worker 2 reached the barrier.
 * All workers have reached the barrier. Proceeding to the next phase
 * Worker 2 is finishing work.
 * Worker 3 is finishing work.
 * Worker 1 is finishing work.
 */