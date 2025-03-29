# Two Phase Commit 两阶段提交

- 一种分布式一致性协议，用于确保多个节点在事务提交时保持一致。

Prepare Phase 准备阶段

- Coordinator 向所有参与者 Participants 发送 “Ready to commit?” 请求
- 每个参与者检查是否能够执行事务（如资源是否可用、操作是否可行）。
- 参与者响应："可以提交"（Yes/Prepare）或 "不能提交"（No/Abort）。

Commit Phase 提交阶段

- 如果所有参与者都返回 "Yes"，协调者发送 "提交"（Commit） 指令，所有参与者执行事务并最终确认。
- 如果有任何参与者返回 "No"，协调者发送 "回滚"（Abort） 指令，所有参与者撤销事务，恢复原状。

Pros:

1. 确保了一致性，所有节点要么全部提交，要么全部回滚

Cons：

1. Blocking，协调者崩溃时，参与者可能一直等待，导致系统无法继续
2. 单点故障：协调者是关键角色，failure 会影响进度
3. 性能开销大： 两轮通信增加了事务延迟，影响吞吐量。

"某种意义上，复述是我大脑 process 事情的途径"

- Classic distributed transaction consensus algorithm
- Widely implemented: - major relational database - messaging systems
  Java: Transaction API and Java Transaction Serice in JEE
  XA protocol:
- Open standard for interoperability
- Support transactions across heterogeneous databases

Coordinator Role: 协调者

1. Allocates a unique transaction id (tid)
2. tid identifies persistent transaction context
3. context records transaction participant

Participant 参与者：

1. Acquire locks on update objects
2. Update not visible until transaction commits

Failure Modes
Participant failure doesn't threaten consistency

1. Fails before prepare phase: coordinator rolls back transaction
2. Fails after replying to prepare:
   a. May commit or abort depending on participant responses
   b. On recovery, ask coordinator for transaction outcome

Coordinator - no simple solution

1. participants blocked until coordinator recovers
2. 2PC not tolerant to coordinator failure:
   a. SPoF
   b. Systems can't make progress

Solution to Coordinator Failure:

1. 3PC
2. replicate coordinator state -> treat as leader-follower system; replica can be promoted to coordinator in case if failure
3. Paxois/Raft

# Consensus Properties

1. Safety: All replicas agree on same winning bid
2. Liveness: a single winning bid is eventually agreed
3. Correctness: the winning bid is one of the bids submitted

# Fault Tolerant Consensus Algorithm:

known variously as:

1. Atomic broadcast
2. Total order broadcast
3. Replicated state machines

Updates/states delivers to multiple nodes

1. exactly once
2. in same order

on leader failures:

1. detect leader failure
2. select new leader from followers
3. recovery protocol to ensure all replicas consistent

followers fail

1. algorithms makes progress with quorum of follower

# Raft (Fault Tolerant Consensus Algorithms)

一致性算法，用于确保分布式系统中的多个节点能够在面对故障时达成共识。

共识过程分为三个部分

# Leader Election 领导选举

- 在集群启动或领导者失效时，节点会进行选举，产生新的 Leader。
- 候选者（Candidate）在超时后发起选举，得到多数投票后成为 Leader。

# Log Replication 日志复制

- Leader 负责接收客户端请求，并将日志条目（log entries）复制到 Follower 节点。
- 只有当多数节点确认日志条目后，Leader 才会提交日志并应用到状态机。

# Satefy 安全性

- Raft 保证日志的一致性：Follower 只能接受来自合法 Leader 的日志，且日志顺序严格一致。
- 通过 日志匹配（Log Matching） 规则确保日志不会分叉。

Leader（领导者）：负责处理客户端请求，并同步日志给 Follower。
Follower（跟随者）：被动接受 Leader 发送的日志，若 Leader 失联则可参与选举。
Candidate（候选者）：在选举阶段尝试获取多数选票，以成为 Leader。

# Netty

Asynchronou event-driven network application framework for building high-performance, scalable network applications in Java. It’s widely used for handling network protocols like HTTP, WebSockets, and custom TCP/UDP applications.

Key Features:

1. Non-blocking I/O (NIO): Uses Java NIO for high scalability.
2. Event-driven architecture: Uses an event loop model for efficient processing.
3. Pipeline-based processing: Allows flexible protocol handling.
4. Thread management: Efficient thread pooling to optimize resource usage.
5. Supports multiple protocols: HTTP, WebSockets, custom TCP/UDP protocols.
