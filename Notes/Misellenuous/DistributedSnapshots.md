# Global State

1. It's the combined state of all processes and all communication channels at a moment in a distributed system.
2. Needed for things like checkpointing, deadlock detection, rollback recovery, etc.
3. But! Capturing it is tricky because there's no global clock in distributed systems.

# Distributed Snapshot

1. A method to record a consistent global state without stopping the system.
2. Famous algorithm: Chandy-Lamport Algorithm:
   - Processes record their own states.
   - Also record messages "in transit" (messages sent but not yet received).
   - Special marker messages are used to coordinate snapshot collection.

# How Chandy-Lamport works (super simple steps):

1. Initiator records its state and sends a marker to all neighbors.
2. When a process receives a marker for the first time:
   - It records its own state.
   - Then sends markers to its neighbors.
3. If a process gets a marker on a channel it already recorded state for:
   - It records the messages received on that channel after it saved its state but before getting the marker.
4. Consistency is maintained: the snapshot may not capture a real-time instant but still represents a valid state that could have occurred!
