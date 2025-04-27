# Physical Time

- Based on real-world clocks (e.g., system clocks, NTP servers).
- Goal: Synchronize all machine clocks as closely as possible.
  Challenges:

1. Network Delays
2. Clock drift(hardware clocks tick at slightly different rates).
   Example:

- Using NTP - Network Time Protocol to sync all servers' clock

# Logical Time

- based on the ordering of events, not actual clock times
- Helps understand which event happened before another, even if clocks aren't synced
- Two key models:
  1. Lamport Timestamps
     - each event gets timestamp
     - if event A happens before B, then timestamp(A) < timestamp(B).
  2. Vector Clocks:
     - Track causality more precisely
     - Each process keeps a vector (array) of counter for all processes.
