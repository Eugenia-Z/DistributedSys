# Networking basics

# WANs (Wide Area Networks) 广域网

- High speed data 'pipelines' connecting continents with fiber optic cables
- Cables support "wavelength division multiplexing"
  - upto 171 Gbps over 400 channels
  - ~70 Terabits per second(Tbps) of bandwidth for a single fiber link

# Wireless Networking - WIFI

- Wi-Fi protocol,802.11ac, allows for maximum (theoretical) data rates of up to 5,400 Mbps
- The most recent 802.11ax protocol, promises increased throughput speeds of up to 9.6Gbps.

# Cellular Networks 蜂窝移动网络

- uses radio waves to send data from phones to routers mounted on cell towers., connected by wires to the core
- 4G LTE is 10 times faster than 3G
- 5G cellular networks promise 10x bandwidth improvement over 4G.

# IP Suite Layers

- 4 Abstract Layers
  - Data link layer 数据链路层: specifies communications across a single network segment.
    - Ethernet 以太网
    - WiFi 无线局域网
  - Internet(IP) layer 网络层： Specifies addressing and routing protocols
    - IP: Internet protocol
    - ICMP:互联网控制报文协议
  - Transport Layer 传输层: specifies protocols for reliable and best-effort host-to-host communications
    - TCP: 传输控制协议
    - UDP: 用户数据报协议
  - Application Layer 应用层： HTTP, HTTPS, SMTP, FTP etc.

# Internet Protocol

- The Internet Protocol suite specifies:

  - Host addressing
  - Data transimission formats
  - Message routing and delivery characterstics

- every device has its own Internet Protocol Address
- The location of an IP address stored in a directory service: Domain Naming Service (DNS) - a widely distributed, hierarchical database
- The address book of the internet

- IP packet structure
  • data to be delivered
  • header data including source and destination IP addresses.

- Data sent by an application is broken up into packets which are independently transmitted across the Internet.

# IP Packet Switing

- best-effort delivery protocol. Doesn't recover from transmission errors such as:
  - Data corruption
  - Packet loss
  - Duplication
- Every packet is routed from source to destination independently
  - out-of-order delivery to the receiver
  - allows network to dynamically respond to conditions such as link failure and congestion
- IP is unreliable

# TCP - Transmission Control Protocol

- connection-oriented
- stream-oriented
- reliable

- 3 handshake to establish a two-way connection between client and server

- Each packet has source and destination address, used by the IP protocol to route the message across the network
- Uses a sequence number so server can reassemble packes into a stream that is identical to the order they were sent.
- Connection stays open until closed by client.

Cumulative achknowledge mechanism for reliable delivery"

- Receiver periodically sends an acknowledgement packet that contains the highest sequence number of received packets.
- Implicitly acknowledges all packets sent with a lower sequence number.
- If a sender doesn’t receive an acknowledgement within a timeout period, the packet is resent ➔ can lead to duplicates

Other features:

- checksums to check packet integrity
- dynamic flow control

- TCP a relatively heavyweight protocol that trades off reliable over efficiency

# UDP - User Datagram Protocol

- Simple connectionless, unreliable protocol
- No guarantee of (in order) delivery
  - No acknowledgements from server
- Exposes the user's program to any unreliability of the underlying network.
  - Thin layer on top of the underlying IP protocol
  - Trades off raw performance over reliability.
- Streaming audio, movies, video conferencing and gaming, are all UDP-appropriate applications
