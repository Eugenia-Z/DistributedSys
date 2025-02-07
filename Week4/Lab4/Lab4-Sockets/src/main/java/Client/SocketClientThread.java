package Client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.CyclicBarrier;

// Sockets of this class are coordinated by a CyclicBarrier which pauses all threads until the last one completes.
// At this stage, all threads terminate
public class SocketClientThread extends Thread{
    private long clientID;
    String hostName;
    int port;
    CyclicBarrier syncBarrier;


    public SocketClientThread(String hostName, int port, CyclicBarrier barrier) {
        this.hostName = hostName;
        this.port = port;
        clientID = Thread.currentThread().getId();
        syncBarrier = barrier;
    }

    public void run() {
        try{
            // TODO: insert code to pass 1k message to the SocketServer
            Socket socket = new Socket(hostName, port);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }

}
