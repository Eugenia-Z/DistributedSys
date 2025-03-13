# Netty

Asynchronou event-driven network application framework for building high-performance, scalable network applications in Java. It’s widely used for handling network protocols like HTTP, WebSockets, and custom TCP/UDP applications.

Key Features:

1. Non-blocking I/O (NIO): Uses Java NIO for high scalability.
2. Event-driven architecture: Uses an event loop model for efficient processing.
3. Pipeline-based processing: Allows flexible protocol handling.
4. Thread management: Efficient thread pooling to optimize resource usage.
5. Supports multiple protocols: HTTP, WebSockets, custom TCP/UDP protocols.

# Raft

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
