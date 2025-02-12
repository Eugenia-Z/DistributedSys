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

# UDP 通信（使用 DatagramSocket）

UDP (User Datagram Protocol) 是一种无连接、不可靠但高效的数据传输协议。适用于 实时通信、流媒体、在线游戏、DNS 解析 等场景。

Java 使用 DatagramSocket 进行 UDP 传输，UDP 不需要建立连接，服务器和客户端只需监听端口并发送数据包。主要组件：

DatagramSocket 发送/接收 UDP 数据
DatagramPacket 存储发送/接收的数据

进阶优化
✅ 实现 UDP 服务器对客户端的回应
✅ 优化 UDP 可靠性（如重传机制）
✅ 结合 Netty 实现高性能 UDP 服务
✅ 支持 组播（Multicast）进行多播通信

# SSL 安全 Socket（SSLSocket）

SSLSocket 是 Java 提供的一种基于 SSL/TLS 的安全通信机制，适用于 加密数据传输，如 HTTPS、加密聊天、金融交易、企业级安全通信 等。

📌 为什么使用 SSLSocket？
在普通 Socket 连接中：

数据是明文传输，容易被中间人攻击（MITM）。
身份未验证，无法确认对方是否可信。
数据完整性无法保证，可能被篡改。

使用 SSLSocket，可以：
✅ 加密通信（防止窃听）
✅ 身份认证（防止伪造服务器）
✅ 数据完整性（防止篡改）

在本地使用 keytool 生成 SSL 证书：

```sh
keytool -genkey -keyalg RSA -alias mycert -keystore keystore.jks -storepass changeit -validity 365

```

# 非阻塞 IO（NIO）（Selector + Channel）

# WebSocket 实时通信

- 全双工(双向通信， Client/Server 都能主动发送消息)、场连接 低延迟、实时通信协议，用于聊天室、实时股票行情、在线协作、游戏等

VS http:

- 短连接（每次请求都重新建立连接）
- 单向通信（Client request， server response）
- 高延迟（每次请求需建立连接）
- 非实时应用（网页浏览，API 请求）
