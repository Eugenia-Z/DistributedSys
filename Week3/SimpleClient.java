import java.io.*;
import java.net.*;

public class SimpleClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;
        try (Socket socket = new Socket(host, port)) {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // send message to server
            output.println("Hello, server!");
            String response = input.readLine();

            // read response from server
            System.out.println("Received from server: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
