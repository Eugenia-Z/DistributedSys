# Socket

- Socket is an endpoint for communication between two machines (or processes) over a network.
- Socket provide a low-level interface to send and receive data across networks using protocols like TCP or UDP
- Allows two programs (usually on different computers) to talk to each other over the internet or a local network.

# Types of Sockets
1. Stream Socket(TCP Socket):
   - Use TCP (transmission Control Protocol) for reliable, connection-oriented communication
   - Data is delivered in the same order it was sent
   - Example: Web browsing (HTTP over TCP)
2. Datagram Socket(UDP Socket):
   - Use UDP(User Datagram Protocol) for fast, connectionless communication
   - No guarantee of order or delivery
   - Example: Video streaming, online gaming

# Server Socket
- Waits for incoming client connections
- Acts as a listener that binds to a specific port and IP address and listens for connection requests
- Once a request is received, it accepts the connection and creates a new socket to handle communication with the client

# Key Methods in Java (ServerSocket class):
- ServerSocket(int port) – Creates a server socket bound to a specified port.
- accept() – Waits for a client to connect and returns a new socket for communication.

# Client Socket
- initiate a connection to a server socket
- The client specifies the server's IP address and port to establish the connection

# Key Methods in Java (Socket class):
- Socket(String host, int port) – Creates a socket and connects it to the specified host and port.
- getInputStream() – Returns an input stream to receive data.
- getOutputStream() – Returns an output stream to send data.


Client Socket: one socket per client
Server Socket: listens continuously, spawns new sockets for each client. 