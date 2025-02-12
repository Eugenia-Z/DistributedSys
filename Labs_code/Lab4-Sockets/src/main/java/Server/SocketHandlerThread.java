package Server;

import java.io.*;
import java.net.Socket;

/**
 * Simple thread to handle a socket request
 */
public class SocketHandlerThread extends Thread{
    private final Socket clientSocket;
    private boolean running = true;
    private final ActiveCount threadCount;

    public SocketHandlerThread(Socket clientSocket, ActiveCount threadCount) {
        this.clientSocket = clientSocket;
        this.threadCount = threadCount;
    }
    public void run() {
        threadCount.incrementCount();
        System.out.println("Accepted Client: Address - " + clientSocket.getInetAddress().getHostName());
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            String clientID = in.readLine();
            System.out.println("Client ID is: " + clientID);
            out.println("Active Server Thread Count = " + Integer.toString(threadCount.getCount()));
            out.flush();
            System.out.println("Reply sent");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        threadCount.decrementCount();
        System.out.println("Thread exiting");
    }
}
