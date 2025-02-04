# SSL: Secure Sockets Layer

- SSL is the predecessor to TLS (Transport Layer Security).
- It was developed by Netscape in the 1990s to secure online communications, especially for websites.
- SSL uses certificates and public-key cryptography to ensure the confidentiality and authenticity of data transmitted between clients and servers.
- SSL Versions: SSL 1.0, SSL 2.0, and SSL 3.0 (which have all been deprecated due to security vulnerabilities).

# TLS: Transport Layer Security

- TLS is the more secure, modern version of SSL.
- It is essentially an upgrade to SSL, with better encryption algorithms and more robust security features.
- TLS 1.0 was released in 1999, and later versions (TLS 1.1, 1.2, and 1.3) have been introduced to address weaknesses in older versions.
- TLS Versions: TLS 1.0 (obsolete), TLS 1.1 (obsolete), TLS 1.2 (widely used), TLS 1.3 (current standard).

# SSL/TLS Protocol Workflow

- Handshake:

  - The client and server agree on which encryption methods to use. The server sends its SSL/TLS certificate.

- Key Exchange:

  - Using asymmetric encryption (public/private key), both parties securely exchange keys to establish a shared secret.

- Session Encryption:

  - After the handshake, symmetric encryption is used to encrypt the data being transmitted.

# Key Components of SSL/TLS Encryption

- Public Key Cryptography:
  The client uses the server’s public key to encrypt data (e.g., for the handshake). Only the server can decrypt it using its private key.

- Symmetric Encryption:
  Once the connection is established, symmetric encryption (like AES) is used for efficient data transfer.

- Digital Certificates:
  Certificates (X.509) authenticate the server’s identity. They contain the server's public key and are issued by trusted Certificate Authorities (CAs).

# 5. SSL/TLS Handshake Example

When you visit a website with HTTPS, the following steps occur:

- Client Hello:

  The client sends a message to the server, specifying supported SSL/TLS versions, cipher suites, and random data.

- Server Hello:

  The server responds with the chosen protocol version, cipher suite, and its public key.

- Certificate Exchange:

  The server sends its SSL/TLS certificate, containing its public key and information about its identity.

- Key Exchange:

  The client uses the server’s public key to securely send a "pre-master secret." Both sides derive the session keys from this.

- Finished:

  Both parties signal that the handshake is complete, and encrypted communication can begin.

# SSL/TLS in Action (HTTPS)

When you visit a secure website (e.g., https://www.example.com), HTTPS ensures that:

1. Encryption: Your data (like login credentials, payment information) is encrypted during transmission, making it unreadable to unauthorized parties.
2. Authentication: The server you are communicating with is verified by a trusted CA, ensuring you are not communicating with a malicious server.
3. Data Integrity: SSL/TLS guarantees that the data sent and received has not been tampered with.

# Summary

1. SSL is the older protocol, TLS is the more secure version.
2. TLS/SSL encryption ensures confidentiality, integrity, and authentication during communication.
3. It is commonly used for HTTPS, securing web traffic.
4. It involves asymmetric encryption for key exchange and symmetric encryption for actual data transfer.
