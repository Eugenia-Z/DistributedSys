# Jedis is the API that Java client uses to interact with Redis

## Connection

```java
Jedis jedis = new Jedis("localhost", 6379); // 连接到 Redis 服务器
jedis.auth("yourpassword"); // 如果有密码

```

## String

```java
jedis.set("key", "value");  // 设置 key 对应的值
String value = jedis.get("key"); // 获取 key 的值

jedis.incr("counter");  // 递增 1
jedis.decr("counter");  // 递减 1
jedis.incrBy("counter", 5); // 递增 5

```

## List

```java
jedis.rpush("listKey", "a", "b", "c"); // [a, b, c] 从右侧插入
jedis.lpush("listKey", "x"); // [x, a, b, c] 从左侧插入

List<String> list = jedis.lrange("listKey", 0, -1); // 获取整个列表，0是起始索引，-1是结束索引，表示到最后一个元素

String left = jedis.lpop("listKey");  // 从左侧弹出
String right = jedis.rpop("listKey"); // 从右侧弹出

```

## Set

```java
jedis.sadd("setKey", "apple", "banana", "cherry"); // 添加到集合
Set<String> set = jedis.smembers("setKey");
boolean exists = jedis.sismember("setKey", "apple"); // true
long size = jedis.scard("setKey"); // set cardinality , 返回集合setKey中的元素个数

```

## Hash

hset() 用于设置哈希表中指定字段的值
在哈希表 user:1001 中设置字段 name 的值为 Alice
user:1001 是哈希表的键； 值是 name 字段和 Alice 的键值对。

hgetAll() 返回哈希表中的所有字段及其对应的值。返回一个 Map<String, String> 类型的对象，包含了所有字段和字段对应的值。

```java
jedis.hset("user:1001", "name", "Alice"); // 这里的键为 user:1001，可以理解为一个用户的标识符。
jedis.hset("user:1001", "age", "25");
String name = jedis.hget("user:1001", "name"); // Alice
Map<String, String> user = jedis.hgetAll("user:1001"); // 返回结果: {name=Alice, age=25, email=alice@example.com}
```

通过 user:1001 可以表示用户 ID 为 1001 的用户数据，而哈希表可以存储多个字段，如 name, age, email 等，便于管理。

## SortedSet 有序集合

- zadd() 是 Redis Sorted Set（有序集合）数据结构的命令，用于向有序集合中添加元素，并且设置元素的分数（score）。
- "ranking": 有序集合的键（key），表示存储玩家排名的有序集合。
- 100: 这是元素 "player1" 的分数（score），用于排序。
- "player1": 这是要加入有序集合的元素，代表一个玩家。

```java
jedis.zadd("ranking", 100, "player1");
jedis.zadd("ranking", 200, "player2");

Set<String> rankings = jedis.zrange("ranking", 0, -1); // 按分数升序
Set<String> topPlayers = jedis.zrevrange("ranking", 0, 2); // 按分数降序，取元素从索引0到索引2，取前三

```

## Transactions

```java
Transaction txn = jedis.multi(); // 开启一个 Redis 事务。该方法会返回一个事务对象 txn，事务中的所有命令会被缓存在内存中，而不会立即执行。
txn.set("foo", "bar"); // 事务中的一条命令
txn.incr("counter"); // 事务中的一条命令
txn.exec(); // 执行事务；之前缓存的命令会按照顺序执行，并返回结果

```

## Connection Pool

```java
JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
try (Jedis jedis = pool.getResource()) {
    jedis.set("foo", "bar");
    System.out.println(jedis.get("foo"));
}
pool.close();

```

# 层级嵌套

组织和存储相关数据，类似路径/命名空间

```java
String verticalKey = String.format("skier:%d:%s:%d:verticals", skierID, seasonID, dayID);
```

skier:{skierID}:{seasonID}:{dayID}:verticals
"skier:123:2025:10:verticals"
这个键可以用于存储与滑雪者 123 在 2025 年的第 10 天滑雪的垂直滑行数据相关的信息。

- {skierID}:{seasonID}:{dayID} 这样的键是由这三个变量（滑雪者 ID、季节 ID、日期 ID）共同决定的。通过这些变量，可以唯一标识特定滑雪者在特定季节和日期的某项数据
- skier: 这个字段就是为了在键名中引入命名空间，使得不同类型的数据能够清晰地区分开来，避免冲突，并且增加数据模型的可扩展性
  - 命名空间（Namespace）：使用 skier: 前缀作为命名空间，可以将与滑雪者相关的数据与其他类型的数据区分开来。
  - 数据分组和可扩展性：如果没有 skier: 这个前缀，所有的键就直接是 {skierID}:{seasonID}:{dayID} 这样的格式。当数据量变大时，随着滑雪者、季节和日期的增加，可能会产生很多类似的键，导致数据管理变得困难。
  - skier: 让你的键具有更多的上下文信息，帮助开发者更容易理解数据的含义。例如，skier:123:2025:10:verticals 就明显表达了这个键是关于滑雪者 ID 为 123 在 2025 年 10 日的垂直滑行数据。
