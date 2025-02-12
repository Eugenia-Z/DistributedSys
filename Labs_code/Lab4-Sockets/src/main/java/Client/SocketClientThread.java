package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

// Sockets of this class are coordinated by a CyclicBarrier which pauses all threads until the last one completes.
// At this stage, all threads terminate
public class SocketClientThread extends Thread{
    private long clientID;
    private String hostName;
    private int port;
    private CyclicBarrier syncBarrier;

    private final static int NUM_INTERATIONS = 1000;


    public SocketClientThread(String hostName, int port, CyclicBarrier barrier) {
        this.hostName = hostName;
        this.port = port;
        clientID = Thread.currentThread().getId(); // thread id
        syncBarrier = barrier;
    }

    public void run() {
        try{
            Socket socket = new Socket(hostName, port);
            // BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // TODO: insert code to pass 1k message to the SocketServer
            for (int i = 0; i<NUM_INTERATIONS; i++ ){
                out.println("Client " + clientID + " message " + i);
            }

            socket.close(); // Close connection after sending message
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        } finally {
            // wait at barrier to sync with other threads
            try{
                // TODO: insert code to wait on the CyclicBarrier
                System.out.println("Thread waiting at barrier");
                syncBarrier.await(); // this notifies the CyclicBarrier that another thread has reached the sync point
            } catch (InterruptedException | BrokenBarrierException ex) {
                Logger.getLogger(SocketClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

// Run netstat -an | grep 12031 to check open connections