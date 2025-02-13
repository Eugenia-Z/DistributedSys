# Example 1: Auto-increment RecordID as Primary Key

## Pros:

1. Uniqueness Guaranteed – The RecordID ensures each row is uniquely identifiable.
2. Simplicity – It is easy to manage and reference in other tables as a foreign key.
3. Efficiency in Indexing – Primary keys based on auto-incremented integers provide efficient indexing and fast lookups.
4. Flexibility – Allows duplicate entries for the same student and problem at different times without issues.

## Cons:

1. No Natural Uniqueness – The RecordID does not inherently represent the data itself, meaning duplicate entries for a student.
2. solving the same problem at the same time would be allowed unless additional constraints are applied.
3. Extra Column Needed – The RecordID is solely for uniqueness and does not contribute to business logic.

## Example 2: Composite Primary Key ([Student, ProblemID, SubmitTime])

## Pros:

1. Enforces Uniqueness on Meaningful Data – Prevents duplicate submissions with the same Student, ProblemID, and SubmitTime.
2. Avoids Extra Column – No need for an artificial RecordID, saving storage space.
3. Better Data Integrity – Ensures no duplicate records exist with the same Student, ProblemID, and SubmitTime.

## Cons:

1. Increased Index Size – Composite keys require more storage and can slow down indexing compared to a single integer key.
2. Foreign Key Complexity – If other tables reference this table, they must include all three fields as a foreign key, making joins more complex.
3. Potential Collisions – If SubmitTime has limited precision (e.g., recorded at the minute level), different submissions within the same minute might be considered duplicates.

# Recommendation

1. If you need a simple and scalable solution, Example 1 (Auto-increment RecordID) is preferred.
2. If you want strict enforcement of unique submissions per student/problem/time and don’t anticipate frequent joins, Example 2 (Composite Key) might be better.
3. For most cases, Example 1 is the better choice unless uniqueness constraints on (Student, ProblemID, SubmitTime) are a critical requirement. You can always add a unique constraint on (Student, ProblemID, SubmitTime) while still using an auto-increment RecordID to get the best of both worlds.

# my slap

1. Load testing your database before deploying.
2. Comparing performance before and after index optimizations.
3. Identifying slow queries affecting response times.
4. Testing server performance under concurrent load.

# Common Usage Examples

1. Basic Benchmark with Auto-Generated Queries

```shell
mysqlslap --user=root --password --host=localhost --concurrency=10 --iterations=5 --auto-generate-sql
```

Simulates 10 concurrent users.
Runs the test 5 times.
Automatically generates test queries.

2. Benchmark with Custom Queries

```shell
mysqlslap --user=root --password --host=localhost --concurrency=5 --iterations=2 --query="SELECT COUNT(*) FROM my_table;"
```

Runs SELECT COUNT(\*) query on my_table.
Simulates 5 users executing the query.
Repeats the test 2 times.

3. Using a SQL Script for Table Creation & Query Execution

```shell
mysqlslap --user=root --password --host=localhost --concurrency=3 --iterations=1 \
  --create="/Users/myuser/create_table.sql" \
  --query="/Users/myuser/test_queries.sql" \
  --no-drop
```

Reads the table creation script from create_table.sql.
Executes queries in test_queries.sql.
Prevents table from being dropped after testing.

# ===========================================

# ===== 11Feb Lab5 mysqlslap load test =====

# ===========================================

eugenia@Eugenias-MacBook-Air / %

```shell
/usr/local/mysql-8.4.2-macos14-arm64/bin/mysqlslap --concurrency=1 --iterations=1 \
--create-schema=mytestdb \
--create='/Users/eugenia/Documents/DistributedSys-1/Week5/create_table1.sql' \
--query='/Users/eugenia/Documents/DistributedSys-1/Week5/fake_records.sql' \
--password --delimiter=";" --no-drop
```

Benchmark
Average number of seconds to run all queries: 0.002 seconds
Minimum number of seconds to run all queries: 0.002 seconds
Maximum number of seconds to run all queries: 0.002 seconds
Number of clients running queries: 1
Average number of queries per client: 1

eugenia@Eugenias-MacBook-Air / %

```shell
/usr/local/mysql-8.4.2-macos14-arm64/bin/mysqlslap --concurrency=1 --iterations=1 \
--create-schema=mytestdb \
--create='/Users/eugenia/Documents/DistributedSys-1/Week5/create_table2.sql' \
--query='/Users/eugenia/Documents/DistributedSys-1/Week5/fake_records.sql' \
--password --delimiter=";" --no-drop
```

Benchmark
Average number of seconds to run all queries: 0.000 seconds
Minimum number of seconds to run all queries: 0.000 seconds
Maximum number of seconds to run all queries: 0.000 seconds
Number of clients running queries: 1
Average number of queries per client: 1

/usr/local/mysql-8.4.2-macos14-arm64/bin/mysqlslap --user=root --password=dbms2024oct --host=localhost --port=3306 --concurrency=50 --iterations=200 --query="SELECT \* FROM mytestdb.records WHERE ProblemID = 111 AND PassOrFail=1"

# Document

https://dev.mysql.com/doc/refman/8.0/en/mysqlslap.html
