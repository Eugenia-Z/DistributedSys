# CAP THEOREM

一个分布式系统不可能同时满足以下三大特性：

1. 一致性（Consistency, C）

- 所有节点在同一时间看到的数据必须是相同的（即读操作要么返回最新写入的数据，要么失败）。
- 类似于传统的 SQL 数据库，每次读取都会得到最新的数据。

2. 可用性（Availability, A）

- 每个请求都必须得到非错误的响应（即使部分节点宕机）。
- 即保证系统始终可用，不会拒绝请求，但可能返回旧数据。

3. 分区容忍性（Partition Tolerance, P）

- 系统能够容忍网络分区（即部分节点之间的网络连接可能断开）。
- 在实际的分布式环境中，网络分区是不可避免的（例如数据中心故障、网络抖动等）。

核心观点：在实际分布式环境中（P 必须存在），我们只能在 C 和 A 之间做出选择。

# 🎯 CAP 定理的三种系统架构选择

CA（强一致 & 高可用） ❌ 不可能实现，因为网络分区不可避免
CP（强一致 & 分区容忍） 保证数据一致性，但可能暂时不可用 适用于金融交易、银行系统 HBase、Zookeeper、MongoDB（强一致模式）
AP（高可用 & 分区容忍） 保证系统可用性，但数据可能短暂不一致（最终一致性） 适用于社交媒体、缓存系统 Cassandra、DynamoDB、MongoDB（默认模式）、Redis

# 🚀 经典数据库与 CAP 选择

MySQL CP (默认) 强一致性，分区容忍，但主从复制时可能不可用
MongoDB CP 或 AP 读写设置决定是强一致（CP）还是最终一致（AP）
Cassandra AP 高可用，最终一致性，适用于大规模分布式存储
Redis（主从） AP 高可用但可能返回旧数据
DynamoDB AP 亚马逊 Dynamo 论文提出，基于 Gossip 协议

# 💡 CAP 定理的现实演进：BASE 理论

由于 CAP 限制了分布式系统的选择，实际应用中很多 NoSQL 数据库采用了 BASE（Basically Available, Soft state, Eventually consistent） 理论：

Basically Available（基本可用） – 保证系统始终可以响应请求，即使是部分可用。
Soft State（软状态） – 允许数据在不同节点之间的状态暂时不一致。
Eventually Consistent（最终一致性） – 经过一段时间，所有副本的数据会最终变得一致。
