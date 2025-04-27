# Main Models of Inter-Process Communication:

1. Shared Memory

   - Processes share a region of memory
   - Fast because data doesn't need to be copied
   - Needs synchronization (lcoks, semaphores) to prevent conflicts

2. Message Passing

   - Processes send and receive message via OS
   - Simpler to manage than shared memory
   - Examples: Pipes, Message Queues, Sockets

3. RPC - Remote Procedure Call

   - One process call procedure on another process(likely remote) like a normal function
   - Hides communication details, looks like a local call.

4. Signals

   - simple notifications (signals) sent between processes
   - Often used for interruptions or alerts (kill comman sends a signal)

5. Sockets
   - Network-based communication; processes can be on different machines
   - Supports protocols like TCP/IP
