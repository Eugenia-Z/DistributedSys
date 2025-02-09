# Atomicity, Consistency, Isolation, Durability

# 1. Atomicity (原子性)

    - Definition: A transaction is an indivisible (atomic) unit of work. Either all operations within the transaction succeed, or none take effect.
    - Example: Transferring money between bank accounts:
    - Steps:
        Deduct $100 from Account A.
        Add $100 to Account B.
    - Failure scenario: If step 2 fails after step 1 succeeds, Account A loses money. Atomicity ensures that both steps are completed or rolled back together.
    - Implementation: Transactions are either committed (if all steps succeed) or rolled back (if any step fails).

# 2. Consistency (一致性)

    - Definition: A transaction brings the database from one valid state to another, maintaining all integrity constraints (e.g., foreign keys, unique constraints).
    - Example: In an e-commerce system:
    A product's total stock count should never be negative.
    If a purchase transaction reduces stock below zero, it should be rolled back.
    - Implementation: Ensuring business rules and constraints are met before committing transactions.

# 3. Isolation (隔离性)

    - Definition: Multiple transactions running concurrently should not interfere with each other.
    - Example: Two users simultaneously booking the last available airline seat.

    - If both read the availability before updating, they might both succeed in booking, leading to overbooking.
    - Isolation prevents this by ensuring transactions are properly sequenced.

    - Isolation Levels (in increasing order of strictness):

    - Read Uncommitted – Transactions can read uncommitted changes from other transactions (risk of dirty reads).
    - Read Committed – Transactions only see committed data (avoids dirty reads).
    - Repeatable Read – Prevents non-repeatable reads by ensuring the same data is read consistently within a transaction.
    - Serializable – Ensures complete isolation (most restrictive but slowest).

# 4. Durability (持久性)

    - Definition: Once a transaction is committed, changes must be permanently stored in the database, even in case of power failures or crashes.
    - Example: If a banking transaction is committed and the system crashes, the money transfer must not be lost.
    - Implementation:
    Writing transaction logs to disk before committing (WAL - Write-Ahead Logging).
    Using replication and backups.
