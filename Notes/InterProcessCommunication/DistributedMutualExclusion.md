# 分布式互斥 Distributed Mutual Exclusion

Goal: Make sure only one process at a time can access a shared resource in a distributed system (Hard because there’s no shared memory or global clock!)

## Two Main Approaches:

1. Permission-Based Algorithms
   - A process must get permission from others before entering critical section
   - Ricart-Agrawala algorithm - process sends request to all others, waits fro replies
2. Token-Based Algorithms
   - A token is a special message that grants permission
   - Only the process holding the token can enter the critical section
   - If you don't have the token, must wait or reuqest it

## How Token-Based Mutual Exclusion Works:

1. One unique token circulates among processes.
2. When a process needs the critical section:
3. If it has the token → it enters.
4. If not → it sends a request to get the token.
5. After using the resource, it passes the token to the next waiting process.

## Advantages of token-based methods:

1. Lower message overhead - compared to permission-based
2. No need to ask everyone, just pass the token!
