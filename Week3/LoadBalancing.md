# Load Balancing Policies

Load balancing policy determines how incoming rquests are distributed across multiple servers

1. Round Roin

- request are distributed sequentially in a circular manner across all available servers
- commonly used when servers have similar capacibilities

2. Least Connections

- Routes requests to the server with the fewest active connections
- Suitable for scenarios with varying request processing times

3. IP Hashing

- Hashes the client's IP address to determine which server should handle the request
- Ensures the same client is routed to the same server, supporting session persistence

4. Weighted Round Robin

- similar to round robin, but assigns weights to servers based on their capacity or performance

5. Random Selection

- Selects a server randomly for each request, useful for evenly distributing traffic in a simple setup.
