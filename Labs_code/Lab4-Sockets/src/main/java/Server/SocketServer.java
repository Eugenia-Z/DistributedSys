package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Basic socket server that implements a thread-per-connection model.
 * 1) Starts and listens for connections on port 12031
 * 2) When a connection received, spawn a thread to handle connection
 */
public class SocketServer {
    public static void main(String[] args) throws IOException {
        // Create socket listener
        ServerSocket m_ServerSocket = new ServerSocket(12031);

        // Create object o count active threads
        ActiveCount threadCount = new ActiveCount();
        System.out.println("Server started .....");
        while(true){
            // accept connection and start thread
            Socket clientSocket = m_ServerSocket.accept();

            // multi-thread handled here
            SocketHandlerThread server = new SocketHandlerThread(clientSocket, threadCount);
            server.start();
        }
    }
}
