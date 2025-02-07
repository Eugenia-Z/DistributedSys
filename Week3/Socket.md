# Socket

- Socket is an endpoint for communication between two machines (or processes) over a network.
- Socket provide a low-level interface to send and receive data across networks using protocols like TCP or UDP
- Allows two programs (usually on different computers) to talk to each other over the internet or a local network.

Socket（套接字）是一种网络通信机制，用于在不同计算机或同一计算机上的进程之间进行数据传输。它可以基于 TCP（可靠，面向连接） 或 UDP（不可靠，无连接） 进行通信。

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

在 Java 中，可以使用 java.net 包中的 Socket（客户端）和 ServerSocket（服务器）类来进行基于 TCP 的网络通信。
ServerSocket 服务器监听端口，等待客户端连接
Socket 客户端和服务器用于通信的套接字
accept() 服务器等待客户端连接
InputStream / BufferedReader 读取数据
OutputStream / PrintWriter 发送数据

# 多线程服务器（同时处理多个客户端）

如果一个 ServerSocket 只能处理一个客户端连接，那么它会阻塞，直到该客户端断开，其他客户端就得等着。所以，我们可以使用多线程来处理多个客户端连接，每个客户端连接都会由一个新线程来处理。

- 每次有新客户端连接，就启动一个 SocketHandler 线程。
- 服务器可以同时处理多个客户端，不会阻塞等待单个客户端。
- 每个 SocketHandler 在 run() 方法中独立处理自己的客户端。

# SSL 安全 Socket（SSLSocket）

# 非阻塞 IO（NIO）（Selector + Channel）

# WebSocket 实时通信

- 全双工(双向)、 低延迟、实时通信协议，用于聊天室、实时股票行情、在线协作、游戏等
