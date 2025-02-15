一致性哈希（Consistent Hashing） 是一种用于分布式系统的数据分布策略，特别适用于 分布式缓存（如 Memcached）、分布式数据库（如 Cassandra）、负载均衡 等应用场景

# 🌟 一致性哈希的核心概念

1. 将整个哈希空间映射为一个“哈希环”（Hash Ring）：

- 哈希函数（如 MD5、SHA-1）的值范围通常是 0 ~ 2^m - 1（m 为哈希位数）。
- 这些值构成一个逻辑上的环形结构。

2. 服务器（节点）在环上随机映射一个哈希值：

- 使用 hash(服务器 IP) 确定每个服务器在环上的位置。

3. 数据根据键（Key）进行哈希，并存储到顺时针方向的最近节点：

- 计算 hash(数据 Key)，从哈希环上找到顺时针最近的服务器存储数据。

4. 节点的增删对系统的影响较小：

- 新增节点：只影响哈希环上部分数据的存储位置，减少了数据迁移量。
- 删除节点：仅需将该节点的数据重新映射到下一个节点，而不是整个数据重新分布。

# 🎯 一致性哈希 vs 传统哈希

传统哈希（如取模 hash(key) % N）的缺点：
✅ 当服务器数量变化时，几乎所有数据都要重新分布，导致数据迁移开销大。

一致性哈希的优势：
✅ 减少数据迁移量——只影响环上的局部数据，而非全局数据。
✅ 适用于动态扩展的分布式系统，如缓存服务器、数据库分片等。

# 💡 一致性哈希的改进：虚拟节点

- 问题
  如果服务器数量较少，数据分布可能不均匀（因为服务器哈希值分布不均）。
  例如： 3 台服务器分别映射到哈希环上的位置相差较远，导致数据倾斜（某些服务器存储的数据比其他服务器多）。

- -解决方案
  引入“虚拟节点”（Virtual Nodes）：

为每台服务器生成多个哈希值，均匀分布在哈希环上。
这样可以让数据更加均匀地分配，避免负载不均衡的问题。

# Issues with Consistent Hashing

1. Uneven data distribution ("Hotspot")
   "celebrity accounts get more traffic coming in"
   potential sol: same popular movie hashed into multiple values so that get distributed on different servers

known as Hot Partition:

1. use a load balancer, when CPU utilization reaches over 70% create a new instance
2. Virtual Nodes -> generate multiple hash values for a server so that data can be distirbuted more evenly.

数据分布不均（热点问题）：
如果节点或虚拟节点分布不均衡，某些节点可能会存储过多数据，导致负载不均。
解决方案：
✅ 采用 虚拟节点（virtual nodes），让每个物理节点对应多个哈希位置，均衡负载。
✅ 选择更优的 哈希函数（如 MurmurHash、FNV-1a），避免数据倾斜。
✅ 结合 负载感知调度，根据节点性能分配更多或更少的虚拟节点。

handle weights on the ring itself. or handle weights on the hashing function

2. 扩容/缩容导致的数据迁移
   传统哈希取模（mod N）方式会导致大规模数据重分布，而一致性哈希虽然减少了数据迁移，但仍有部分数据需要移动。
   解决方案：
   ✅ 使用 跳跃一致性哈希（Jump Consistent Hashing），减少数据重分布量。
   ✅ 采用 数据预分片（pre-sharding），将数据划分为固定数量的片（shard），减少节点变化带来的影响。
   ✅ 通过 渐进式扩容（progressive rebalancing），逐步调整负载，而不是一次性迁移大量数据。
3. 虚拟节点管理复杂度高
   虚拟节点的引入可以均衡负载，但如果设置不合理，可能会增加查找开销和管理复杂度。
   解决方案：
   ✅ 设定 合理的虚拟节点数量，通常一个物理节点对应多个虚拟节点（如 100-500 个）。
   ✅ 采用 动态负载均衡算法，根据节点的实际负载调整虚拟节点分配。
   ✅ 使用 客户端缓存（client-side caching），减少频繁的哈希计算和网络请求。
