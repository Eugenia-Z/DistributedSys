Proxy server vs. Reverse Proxy

# Proxy Server 代理服务器

- A computer that acts as an intermediary between a client machine and a server, caching information to save access time
- A client (such as a web browser) sends requests to the proxy, which forwards those requests to the intended server. Once the proxy receives the response from the server, it relays it back to the client.

Use cases:

1. Anonymity: hides the client's IP address from the server
2. Caching: reduces bandwidth usage by caching frequently accessed content
3. Access Control: Enforcing rules to control internet access
4. Load balancing: Distributes outgoing requests across multiple servers

Example:
A user in a corporate network accesses a website through a proxy server. The website sees the proxy's IP, not the user's.

# Reverse Proxy 反向代理

- Acts as a nintermediary between a server and the clients.
- A type of proxy server that retrieves resources on behald of a client from one or mroe servers. These resources are then returned to the client as though they originated from the server itself.

Use cases:

1. Load balancing: distribute incoming traffic across multiple servers
2. Security: Hides the identity and structure of backend servers, protecting them from direct attacks
3. SSL termination: Manages SSL encryption and decryption to reduce the load on backend services
4. Caching: Stores frequently requested content to improve response time.

Example:
A website uses a reverse proxy to distribute incoming requests to multiple backend web servers, improving performance and availability.

# SSL: Secure Sockets Layers

- 安全套接层：一种网络安全协议，用于在互联网上建立加密通道，保护数据传输安全
- Encryption protocal designed to secure communication over the internet.
- deprecated due to decurity vulnerabilities

# TLS: Transport Layer Security

- 传输层安全协议：一种用于在互联网上保护数据传输安全的加密协议，通常用于保护网站和用户之间的通信
- Successor to SSL, stronger encryption, authentication and integrity.
- Modern secure communication uses TLS.
