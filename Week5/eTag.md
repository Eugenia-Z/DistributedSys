# ETag（Entity Tag）—— HTTP 缓存机制

ETag（实体标签） 是 HTTP 头字段之一，用于缓存控制和并发请求管理。它是由服务器生成的资源唯一标识符，用于判断资源是否发生变化，从而减少不必要的数据传输，提高性能。

- ETag 是 HTTP 资源的唯一标识符，用于判断资源是否变更。
- 配合 If-None-Match，可以减少不必要的数据传输，提高缓存效率。
- 比 Last-Modified 更精确，适用于内容变化频繁的场景。
- 现代 Web 应用（如 CDN、API 服务器）广泛使用 ETag 进行缓存优化。

# ETag 的工作原理

1. 客户端第一次请求资源

- 服务器返回资源内容，并在响应头中包含 ETag。

```yaml
HTTP/1.1 200 OK
ETag: "abc123"
Content-Length: 1024
Content-Type: text/html
```

- 其中 ETag: "abc123" 是该资源的唯一标识符（可能是文件的哈希值或版本号）。

2. 客户端缓存 ETag 并发起后续请求

- 客户端再次请求该资源时，会在 If-None-Match 头中携带 ETag：

```yaml
GET /index.html HTTP/1.1
If-None-Match: "abc123"
```

3. 服务器比较 ETag 并返回响应

资源未变更：返回 304 Not Modified，不返回数据，客户端使用缓存。

```yaml
HTTP/1.1 304 Not Modified
```

资源已变更：返回 200 OK 并提供新内容，同时更新 ETag。

```yaml
HTTP/1.1 200 OK
ETag: "xyz789"
Content-Length: 2048
```

# ETag 的作用

- 减少带宽占用：如果资源未变更，服务器返回 304 Not Modified，避免重复传输相同数据。
- 提高性能：客户端可以直接使用缓存，减少服务器负担，加快加载速度。
- 保证数据一致性：ETag 可用于并发控制，防止多个用户同时修改同一资源（类似乐观锁）
