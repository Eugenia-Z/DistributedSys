import java.io.*;
import java.net.*;

public class SimpleServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5500)) {
            System.out.println("Server is listening on port 5000...");

            while (true) {
                Socket socket = serverSocket.accept(); // wait for Client to connect
                System.out.println("Client connected: " + socket.getInetAddress());

                // Handle Client Request 处理客户端请求
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

                String message = input.readLine();
                System.out.println("Received from client: " + message);
                output.println("Hello, client! Message received."); // Send response to client
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
