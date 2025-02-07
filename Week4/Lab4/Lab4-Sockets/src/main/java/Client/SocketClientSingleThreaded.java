package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Simple client to send request via a socket.
 * Accepts host and port via command line, defaults to localhost and port 12031
 */
public class SocketClientSingleThreaded {
    public static void main(String[] args) {
        String hostName;
        int port;

        if (args.length == 2){
            hostName = args[0];
            port = Integer.parseInt(args[1]);
        } else {
            hostName = "localhost";
            port = 12031;
        }

        long clientID = Thread.currentThread().getId();
        try{
            Socket socket = new Socket(hostName, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println(Long.toString(clientID));

            System.out.println(in.readLine());

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
