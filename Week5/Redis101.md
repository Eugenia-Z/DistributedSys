# Redis: Remote Dictionary Server

基于内存：数据存储在 RAM 中，读取速度极快（纳秒级）。 每秒读 11 万次，写 8 万次。

支持多种数据结构：不仅是简单的 key-value 存储，还支持列表、哈希、集合、有序集合等。
持久化：支持 RDB（快照）和 AOF（日志追加）持久化方式。
支持分布式：可以通过 主从复制（Replication）、集群（Cluster） 构建高可用架构。

# 核心数据结构

数据类型 命令示例 适用场景
String SET key value / GET key 缓存数据、计数器
List（列表） LPUSH list value / LRANGE list 0 -1 消息队列、任务调度
Hash（哈希） HSET user:1 name "Alice" / HGET user:1 name 存储对象，如用户信息
Set（集合） SADD tags "tech" / SMEMBERS tags 标签系统、去重
Sorted Set（有序集合） ZADD leaderboard 100 "Alice" / ZRANGE leaderboard 0 -1 WITHSCORES 排行榜、评分系统
Bitmaps（位图） SETBIT users_online 1001 1 统计、签到系统
HyperLogLog PFADD visitors "user123" 统计唯一访问用户（大数据场景）
Geo（地理位置） GEOADD cities 13.361389 38.115556 "Palermo" 地理位置存储和查询

# caching patterns

解决缓存与数据库之间同步的问题

## Cache Aside 旁路缓存

机制：
应用先查询缓存，如果缓存命中（Cache Hit），直接返回数据。
如果缓存未命中（Cache Miss），从数据库查询数据，并主动写入缓存，然后返回数据给用户。
写操作时，先更新数据库，再删除缓存，让下次查询时重新加载最新数据（Cache Invalidation）。

适用场景：
适用于 读多写少 的场景，比如商品详情页、用户资料等。
避免脏数据，保证数据库的一致性。

优缺点：
✅ 只在需要时缓存数据，节省内存。
✅ 避免缓存污染，减少不常用数据的缓存占用。
❌ 可能会导致缓存短暂不一致（写后查询可能命中旧数据）。
❌ 可能引发 缓存雪崩（大量请求同时查询数据库）。

```python
def get_data(key):
    value = redis.get(key)
    if value is None:  # 缓存未命中
        value = db.query("SELECT * FROM table WHERE key = ?", key)
        redis.set(key, value, ex=3600)  # 设置过期时间
    return value
```

## Write Through 写穿透

机制：
写操作时，同时写入数据库和缓存，保证缓存与数据库同步。
读操作, 仍然优先查询缓存，缓存命中直接返回数据。

适用场景：
适用于 写频繁 的场景，比如计数器、用户状态等。
适用于 数据一致性要求高 的应用。

优缺点：
✅ 读取数据时保证缓存始终是最新的。
✅ 适用于数据变更较少的场景，避免频繁查询数据库。
❌ 写性能较差，因为每次写都需要同步更新缓存。
❌ 可能会缓存大量无用数据（即使很少被访问）。

```python
def update_data(key, value):
    db.update("UPDATE table SET value = ? WHERE key = ?", value, key)
    redis.set(key, value)
```

## Read Through 读穿透

机制：
由缓存层（如 Redis）主动与数据库交互，而不是应用程序自己查询数据库。
如果缓存命中，直接返回数据。
如果缓存未命中，由缓存层负责从数据库加载数据并返回，同时写入缓存。

1. 读取数据时，直接请求缓存
2. 如果缓存未命中，缓存层（而不是应用）从数据库加载数据
3. 数据自动存入缓存，并返回给用户

适用场景：
适用于 CDN 或 外部缓存管理器，如 AWS ElastiCache。
适用于 数据访问逻辑较复杂，不希望应用层自己管理缓存时。

优缺点：
✅ 业务代码无需关心数据库访问逻辑，简化开发。
✅ 可使用第三方服务（如 Redis 代理）来管理数据加载。
❌ 需要特定的缓存库或中间件支持。
❌ 可能引入额外的缓存层逻辑，增加复杂性。

```python
class ReadThroughCache:
    def get(self, key):
        value = redis.get(key)
        if value is None:
            value = self.load_from_db(key)
            redis.set(key, value, ex=3600)
        return value

    def load_from_db(self, key):
        return db.query("SELECT * FROM table WHERE key = ?", key)
```

# Redis Caching

## 1.缓存雪崩

同时大量 Key 失效，导致数据库瞬间高负载。

解决方案：
使用 不同 TTL，避免同时过期。
双缓存策略，异步更新数据。

## 2. 缓存穿透

查询一个不存在的数据，导致请求全部落到数据库。

解决方案：
存储空值，避免频繁查询数据库。
布隆过滤器（Bloom Filter） 拦截无效请求。

## 3. 缓存击穿

某个热点 Key 突然过期，导致大量请求打到数据库。

解决方案：
设置热点数据永不过期，定期手动更新。
互斥锁：缓存失效后，只有一个线程查询数据库，其余等待。

# Redis Persistence

## 1. AOF (Append Only file)

将每一个写操作（如 SET、HSET）以日志形式追加到文件，从而保证数据不会因 Redis 进程退出而丢失。

### AOF 持久化的特点：

1. 追加写入：Redis 只会将写入命令追加到 AOF 文件，不会覆盖或修改已有内容。

2. 可恢复数据：Redis 重启时可以重新执行 AOF 文件中的命令，从而恢复数据状态。

3. 三种写入策略：

   - always（每次写操作后立刻 fsync）：最安全但性能开销最大。
   - everysec（默认，每秒 fsync 一次）：性能与安全性平衡。
   - no（由操作系统决定何时同步）：最高性能，但可能会丢失数据。

4. AOF 重写机制：

   - 随着时间推移，AOF 文件会变大，Redis 提供**AOF rewrite（重写）**机制，它会生成一个新的 AOF 文件，去除冗余命令，保持文件精简。
   - 例如，SET key 1 -> SET key 2，最终 AOF 文件只会保留 SET key 2。

## 2. RBD （Redis Database Snapshot，快照）

- 以二进制快照方式定期将数据存储到磁盘。
- SAVE（同步） & BGSAVE（异步）。
- 适用于备份，恢复速度快。

一般情况下，生产环境同时使用 RDB + AOF，既能保证数据安全，也能兼顾性能。

# High Availablity & Cluster

## ① 主从复制： （Master-Slave Replication）用于高可用和数据同步。

```shell
SLAVEOF <master_ip> <master_port>
```

主（Master） 处理写入请求，并同步给从（Slave）。
从库（Slave） 只读，可用于分流查询请求。

一个主，一个或多个从，从节点在主节点复制数据。主节点负责写，从节点负责读。可以更好的分担主节点的压力，但是如果主节点宕机了，会导致部分数据不同步。

## ② 哨兵模式(重点)：

也是一种主从模式，哨兵定时去查询主机，如果主机太长时间没有相应，多个哨兵就投票选出新的主机。提高了可用性，但是在选举新的主节点期间，还是不能工作。

## ③Cluster 集群模式:

Redis Cluster 是 Redis 的分布式架构，支持数据自动分片。
采用 哈希槽（Hash Slot） 机制，将 16384 个槽分配到不同的 Redis 节点。

采用多主多从(一般都是三主三从)，按照规则进行分片，每台 redis 节点储存的数据不一样，解决了单机储存的问题。还提供了复制和故障转移功能。配置比较麻烦。

```

```
