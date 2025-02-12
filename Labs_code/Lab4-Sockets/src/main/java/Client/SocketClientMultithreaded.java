package Client;

import java.util.concurrent.CyclicBarrier;

/**
 * Skeleton socket client.
 * Accepts host/port on command line or defaults to localhost/12031
 * Then (should) starts MAX_Threads and waits for them all to terminate before terminating main().
 */
public class SocketClientMultithreaded {
    static CyclicBarrier barrier;

    public static void main(String[] args) throws InterruptedException {
        String hostName;
        final int MAX_THREADS = 50;
        int port;

        if (args.length == 2) {
            hostName = args[0];
            port = Integer.parseInt(args[1]);
        } else{
            hostName = "localhost";
            port = 12031;
        }
        barrier = new CyclicBarrier(MAX_THREADS + 1); // main thread also calls barrier.await() after launching all threads


        // TODO: create ad start max_threads SocketClientThread
        for (int i = 0; i < MAX_THREADS; i++) {
            new SocketClientThread(hostName, port, barrier).start();
        }
        // TODO: wait for all threads to complete
        // We need 51 total calls to await(); before the barrier is released:
        // 50 client threads reach the barrier.
        // 1 main thread reaches the barrier.
        // Without +1, the main thread would not be part of the barrier, and could terminate before all client threads finish.
        try{
            barrier.await(); // Once the 51st thread (last one) reaches the barrier, all threads are released.
        } catch(Exception e){
            e.printStackTrace();
        }


        System.out.println("Terminating...");
    }
}
