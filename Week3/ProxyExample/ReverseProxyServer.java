import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ReverseProxyServer {
    public static void main(String[] args) throws IOException {
        int localPort = 8080; // Port for reverse proxy server
        ServerSocket serverSocket = new ServerSocket(localPort);
        System.out.println("Reverse Proxy Server started on port " + localPort);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(new ReverseProxyThread(clientSocket)).start();
        }
    }
}

class ReverseProxyThread implements Runnable {
    private Socket clientSocket;
    public ReverseProxyThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try(Socket backendSocket = new Socket("localhost", 8080);// connection to backend server
            InputStream clientIn = clientSocket.getInputStream();
            OutputStream clientOut = clientSocket.getOutputStream();
            InputStream backendIn = backendSocket.getInputStream();
            OutputStream backendOut = backendSocket.getOutputStream()  ) {

            // Forward request from client to backend server
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = clientIn.read(buffer)) != -1){
                backendOut.write(buffer, 0, bytesRead);
                backendOut.flush();
            }
            // Forward response from bckedn server to client
            while ((bytesRead = backendIn.read(buffer)) != -1){
                clientOut.write(buffer, 0, bytesRead);
                clientOut.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}