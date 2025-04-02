Token Bucket 解决方案

1. 机制

- 你可以把 Bucket (桶) 想象成一个容器，最多可以存放一定数量的 Token (令牌)。
- Token 以固定速率添加到 Bucket 中，表示允许的请求额度。
- 当请求到来时：
  - 如果 Bucket 里有 Token，请求会被允许，并消耗一个 Token。
  - 如果 Bucket 为空（即没有 Token），则请求会被拒绝或者需要等待新的 Token 生成。

2. 关键参数

- 桶的容量 (Bucket Size)：决定了在短时间内允许的最大突发流量。

- 令牌生成速率 (Token Refill Rate)：控制了长期稳定的请求速率。

- 令牌消耗规则：每个请求消耗一个 Token（或不同请求类型消耗不同数量的 Token）。

在 server 端做 token bucket 流控的思路

1. 使用 AtomicInteger 追踪队列中的消息数量
2. 定期补充 Token （每秒/固定间隔增加 Token）
3. 当 Token 数量为 0 时，拒绝请求或让请求等待直到有可用的 Token
4. 当消息成功进入 RabbitMQ 时，减少 token 计数

```java
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SkierServlet extends HttpServlet {
    private static final int BUCKET_CAPACITY = 1000;  // Token bucket 最大容量
    private static final int REFILL_RATE = 50;        // 每秒补充 Token 的速率
    private static final AtomicInteger tokenBucket = new AtomicInteger(BUCKET_CAPACITY);
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            RMQChannelFactory factory = new RMQChannelFactory();
            channelPool = new GenericObjectPool<>(factory);
        } catch (IOException | TimeoutException e) {
            throw new ServletException("Failed to initialize RabbitMQ connection", e);
        }

        // **启动 Token 生成任务**
        // 每秒补充refill_rate = 50 个token （直到1000个token）
        scheduler.scheduleAtFixedRate(() -> {
            int currentTokens = tokenBucket.get();
            int newTokenCount = Math.min(BUCKET_CAPACITY, currentTokens + REFILL_RATE);
            tokenBucket.set(newTokenCount);
        }, 0, 1, TimeUnit.SECONDS);
    }

    /*
     * doPost 里先检查Token数量，如果没有Token，则直接拒绝请求
    */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (tokenBucket.get() <= 0) {
            response.sendError(HttpServletResponse.SC_TOO_MANY_REQUESTS, "Rate limit exceeded. Try again later.");
            return;
        }

        // **减少 Token**
        tokenBucket.decrementAndGet();

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || !isValidPath(pathInfo)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format.");
            return;
        }

        // Parse path parameters
        String[] pathParts = pathInfo.split("/");
        try {
            // Todo
        }
    }
```

Improvement

1. 基于 redis 的分布式 Rate Limiting，支持多个 server 共享 Token 计数
2. 在 scheduler.scheduleAtFixedRate 中 动态调整 REFILL_RATE，根据服务器负载情况自动调节。

# 其他 API Rate Limiting 的常见方案

1.  Token Bucket 令牌桶

    - 适用于突发流量，能平滑请求
    - 维护一个令牌桶，每隔固定时间生成一定数量的 token，请求需要消耗令牌才能执行
    - 如果桶空了，新的请求必须等待新令牌产生，或者被拒绝

2.  Leaky Bucket 漏桶

    - 适用于流量平滑，控制流量输出速率
    - 维护一个固定大小的桶，请求进入桶里，但按照固定速率出桶 - 如果桶满了，新请求会被丢弃或者排队等待

3.  Fixed Window Counter (固定窗口计数)

    - 适用于短时间爆发的限制。
    - 维护一个时间窗口（如 1 秒或 1 分钟），在该窗口内记录请求次数。
    - 如果请求超过设定阈值，则拒绝处理，直到新窗口开启。

4.  Sliding Window Counter (滑动窗口计数)

    - 适用于更精细控制的流量限制。
    - 记录过去一定时间范围内的请求，并动态计算速率。
    - 例如，如果设置 "过去 60 秒最多 1000 次请求"，则每次请求都会重新计算过去 60 秒内的请求数。

5.  Concurrency Limiting (并发限制)
    - 适用于限制并发连接，防止资源耗尽。
    - 限制同时处理的请求数，如数据库连接、线程池等，超过则排队或拒绝。
