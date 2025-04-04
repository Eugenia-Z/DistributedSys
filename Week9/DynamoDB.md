1. pom 里添加依赖，初始化 instance

```java
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

DynamoDbClient dynamoDb = DynamoDbClient.create();
```

# 常见 Query 操作语句和方法

- 查询某用户所有记录: keyConditionExpression 只写 partition key
- 查询时间范围: 配合 sort key 使用 BETWEEN
- 分页: 用 lastEvaluatedKey 和 limit
- 排序: DynamoDB 默认按 sort key 升序
- 倒序: .scanIndexForward(false)
- 返回指定字段: 用 projectionExpression
- 用 GSI 查询非主键字段 指定 .indexName(...)

1. 基于主键 Partition Key 查询

- keyConditionExpression 是必须的，必须提供 partition key

```java
QueryRequest request = QueryRequest.builder()
.tableName("xxx")
.keyConditionExpression("userID = :uid")
.expressionAttributeValues(Map.of(":uid", AttributeValue.fromS("kaiqi123")))
.build()

QueryResponse respoinse = dynamoDb.query(request);
```

2. 查询范围 Range key + 主键

```java
QueryRequest request = QueryRequest.builder()
.tableName("xxx")
.keyConditionExpression("userID = :uid AND createAt BETWEEN :start AND :end")
.expressionAttributeValues(Map.of(
    ":uid", AttributeValue.fromS("kaiqi123"),
    ":start", AttributeValue.fromS("2024-02-03"),
    ":end", AttributeValue.fromS("2024-02-03"),
    ))
.build()

QueryResponse respoinse = dynamoDb.query(request);
```

3. 使用 FilterExpression 进行过滤 （非索引字段）

- FilterExpression 过滤的是已经匹配主键的数据（后过滤），不会影响查询吞吐量消耗

```java
QueryRequest request = QueryRequest.builder()
.tableName("xxx")
.keyConditionExpression("userID = :uid")
.filterExpression("status = :status")
.expressionAttributeValues(Map.of(
    ":uid", AttributeValue.fromS("kaiqi123"),
    ":status", AttributeValue.fromS("ACTIVE"),
    ))
.build()

QueryResponse respoinse = dynamoDb.query(request);
```

4. 分页处理结果 Pagination

```java
QueryRequest request = QueryRequest.builder()
.tableName("xxx")
.keyConditionExpression("userID = :uid")
.limit(20)
.exclusiveStartKey(lastEvaluatedKey) //  optional, for pagination
.expressionAttributeValues(Map.of(
    ":uid", AttributeValue.fromS("kaiqi123"),
    ))
.build()
```

5. 使用 Global Secondary Index 查询

```java
QueryRequest request = QueryRequest.builder()
.tableName("XXX")
.indexName("StatusIndex")
.keyConditionExpression("status = :status")
.expresssionAttributeValues(Map.of(":status", AttributeValue.fromS("PENDING")))
.build()
```

6. ProjectionExpression(只返回部分字段)

```java
QueryRequest request = QueryRequest.builder()
.tableName("XXX")
.keyConditionExpression("userID = :uid")
.projectionExpression("userId, createdAt, status")
.expressionAttributeValues(Map.of(":uid", AttributeValue.fromS("kaiqi123")))
.build()
```

7. 遍历结果

```java
for (Map<String, AttributeValue> item : response.items()) {
    String id = item.get("userId").s();
    String status = item.get("status").s();
    System.out.println(id + " -> " + status);
}

```

本质还是设计表结构，明确要实现的业务逻辑，然后实现 JAVA 查询代码

A4 GETAPI 带分页

```java
public PageResult getLiftRidesForSkier(
    int resortID, String seasonID, int dayID, int skierID,
    Map<String, AttributeValue> exclusiveStartKey, int pageSize) {
  try {
    String sortKeyPrefix = resortID + "$" + dayID + "$";

    QueryConditional keyCondition = QueryConditional
        .sortBeginsWith(k -> k.partitionValue(skierID).sortValue(sortKeyPrefix));

    Expression filterExp = Expression.builder()
        .expression("seasonID = :seasonID")
        .putExpressionValue(":seasonID", AttributeValue.builder().s(seasonID).build())
        .build();

    QueryEnhancedRequest.Builder requestBuilder = QueryEnhancedRequest.builder()
        .queryConditional(keyCondition)
        .filterExpression(filterExp)
        .limit(pageSize);  // 每页数量

    if (exclusiveStartKey != null && !exclusiveStartKey.isEmpty()) {
      requestBuilder.exclusiveStartKey(exclusiveStartKey);  // 上一页的最后一个 key
    }

    SdkIterable<Page<LiftRide>> results = skierLiftRidesTable.query(requestBuilder.build());

    Page<LiftRide> page = results.iterator().next(); // 只取一页

    return new PageResult(
        new ArrayList<>(page.items()),
        page.lastEvaluatedKey()
    );

  } catch (Exception e) {
    e.printStackTrace();
    return new PageResult(new ArrayList<>(), null);
  }
}

```
