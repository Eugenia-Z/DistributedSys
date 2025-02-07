import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

// Simple proxy server in Java
public class ProxyServer {
    public static void main(String[] args) throws IOException {
        int localPort = 8080; // Port for proxy server
        ServerSocket serverSocket = new ServerSocket(localPort);
        System.out.println("Proxy Server started on port " + localPort);

        while(true){
            Socket clientSocket = serverSocket.accept();
            new Thread(new ProxyThread(clientSocket)).start();
        }
    }
}
class ProxyThread implements Runnable {
    private Socket clientSocket;
    public ProxyThread(Socket clientSocket) {
       this.clientSocket = clientSocket;
    }
    public void run() {
        try (Socket serverSocket = new Socket("example.com", 80); // connection to destination server
             InputStream clientIn = clientSocket.getInputStream();
             OutputStream clientOut = clientSocket.getOutputStream();
             InputStream serverIn = serverSocket.getInputStream();
             OutputStream serverOut = serverSocket.getOutputStream()){

            // Forward request from client to server
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = clientIn.read(buffer)) != -1) {
                serverOut.write(buffer, 0, bytesRead);
                serverOut.flush();
            }

            // Forward response from server to client:
            while ((bytesRead = serverIn.read(buffer)) != -1) {
                clientOut.write(buffer, 0, bytesRead);
                clientOut.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
