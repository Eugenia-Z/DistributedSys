Goal: high thoughtput, low latency, fast write, allow queries on skier data.

Choices

# Redis:

Pros:

1. in memory, noSQL, fast write. primairy for caching, limited persistence ability.
2. original rich data structures: hash, sorted set, list

Cons:

1. potential data loss if not turn on AOF and config sync every write
2. Limited queries: no SQL statements, stats computed at application layer

# MySQL

AWS RDB/Posgres/Aurora
Pros:

1. strong SQL queries, join, group by, index
2. Reliable, ACID (support transaction - atomicity,consistency,isolation,durability)
3. Aurora is extensible

Cons:

1. relational database transactions are expensive. high-concurrency write might be the bottleneck. need index optimization
2. MySQL/Postgres hard to scale horizontally
3. Data model needs carefull design -> optimize for JOINS

# DynamoDB

Pros:

1. AWS provided, noSQL, high thoughtput, horizontal scalble (auto-sharding)
2. low latency O(1) query
3. low maintenance

Cons:

1. Limited query -> depends on Partition key + sort key
2. not support JOIN, expensive fancy joins
3. expansive

# Mongo

Pros

1. document-based NoSQL, good for JSON documents
2. flexible schema, JSON natually suitable for skier record
3. higher write throughput than SQL, support batch write
4. support indexing (skierID, season)
5. support aggregation pipeline, stronger query than sql

Cons:

1. eventual consistency, might occur short time data discrenpancy
2. not suitable for strong consistency (financial transaction)
3. weak transaction support (ACID)
