package Client;

import java.util.concurrent.CyclicBarrier;

/**
 * Skeleton socket client.
 * Accepts host/por on command line or defaults to localhost/12031
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
        barrier = new CyclicBarrier(MAX_THREADS);

        // TODO: create ad start max_threads SocketClientThread
        // TODO: wait for all threads to complete

        System.out.println("Terminating...");
    }
}
