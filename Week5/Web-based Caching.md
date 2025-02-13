# 🌐 HTTP/Web-Based Caching

HTTP/Web 缓存是一种存储静态或动态 Web 资源以减少服务器负载并提高访问速度的技术。它可以存储网页、图片、API 响应等，以减少重复请求，提高性能。
HTTP/Web caching is a technique used to store static or dynamic web resources to reduce server load and improve access speed. It can cache web pages, images, API responses, and more to minimize redundant requests and enhance performance.

# 常见的 HTTP/Web 缓存类型

## 1️⃣ 浏览器缓存（Browser Cache）

- 存储在用户本地浏览器中，如 HTML、CSS、JS、图片等。
- 通过 HTTP 头部（如 Cache-Control, Expires）控制缓存策略。
- Example:
  浏览器缓存一个网页的 CSS 文件，使其在下次访问时无需重新下载。

- Stored in the user's local browser, caching assets like HTML, CSS, JS, and images.
- Controlled via HTTP headers (Cache-Control, Expires).
- Example:
  A browser caches a webpage’s CSS file, preventing the need for re-downloading on the next visit.

## 2️⃣ 代理缓存（Proxy Cache）

- 部署在服务器与客户端之间，如 CDN（内容分发网络）。
- 适用于多个用户共享的缓存，提高资源加载速度。
- Example: Cloudflare 或 Akamai 等 CDN 缓存网站资源，以减少服务器压力。

- Placed between the server and clients, like CDNs (Content Delivery Networks).
- Shared caching for multiple users, improving resource delivery speed.
- Example: CDNs like Cloudflare or Akamai cache website resources to reduce server load.

## 3️⃣ 网关缓存（Gateway Cache）（又称反向代理缓存）

- 部署在 Web 服务器前端，例如 Nginx、Varnish Cache。
- 适用于加速动态内容的访问，如 API 响应。
- Example: 使用 Nginx 作为反向代理，缓存 API 响应以减少数据库查询。

- Positioned in front of web servers, like Nginx, Varnish Cache.
- Useful for caching dynamic content, such as API responses.
- Example: Using Nginx as a reverse proxy to cache API responses and reduce database queries.

# 🛠 Key HTTP Headers for Caching

🔹 Cache-Control（控制缓存行为）

Cache-Control: max-age=3600（缓存 1 小时）
Cache-Control: no-cache（可以缓存，但每次扔请求服务器校验缓存）

Cache Directives:

# no-store:

• a resource from a request response must never be stored in any cache (browser, proxy, or intermediate cache).
•Used for sensitive data (e.g., authentication tokens, banking details) to ensure that no copy is stored anywhere.

# no-cache:

• a cached resource must be revalidated with an origin server before use.
• Ensures fresh content while still allowing caching to reduce network requests.）

• private: a resource only be cached by a user-specific device such as a
Web browser
• public: a resource can be cached by any proxy server
• max-age: defines the length of tim

🔹 Expires（指定缓存过期时间）

Expires: Wed, 21 Oct 2025 07:28:00 GMT
Specifies an expiration date for cached resources.

🔹 ETag（实体标签，校验资源是否更改）

ETag: "abc123"（如果资源未更改，则返回 304 Not Modified）
Used to validate if a resource has changed.

🔹 Last-Modified（资源最后修改时间）

Last-Modified: Tue, 01 Feb 2023 10:00:00 GMT
Helps determine if cached content is still fresh.

# Etag

ETag（Entity Tag）是 HTTP 协议中的缓存机制，用于标识资源的唯一版本。它可以帮助浏览器判断服务器上的资源是否发生了变化，从而减少不必要的网络传输，提高性能。

🔹 ETag 作用

1. 避免重复下载：如果资源未变化，浏览器可直接使用本地缓存，而不用重新下载。
2. 减少服务器压力：服务器只需返回 304 Not Modified，而不是重新发送完整资源。
3. 提升用户体验：减少页面加载时间，节省带宽。

## 📌 ETag 的工作流程

📤 第一步：服务器返回 ETag
当浏览器第一次请求资源时，服务器会生成一个唯一的 ETag（通常是资源的哈希值）并返回给客户端。(这里 ETag: "abc123" 代表该资源的当前版本。)

```json
HTTP/1.1 200 OK
ETag: "abc123"  # 资源的唯一标识
Cache-Control: max-age=3600
```

📥 第二步：浏览器使用 If-None-Match 进行验证
当浏览器再次请求相同资源时，它会在请求头中附带 If-None-Match，将之前缓存的 ETag 发送给服务器，询问资源是否有变化。

📤 第三步：服务器返回响应
如果资源未变化（ETag 匹配），服务器返回 304 Not Modified，不再传输资源内容。
如果资源已变化（ETag 不匹配），服务器返回新的资源和新的 ETag。

✅ 未修改：

```json
HTTP/1.1 304 Not Modified
ETag: "abc123"
```

❌ 已修改：

```json
HTTP/1.1 200 OK
ETag: "xyz789" # 资源更新了，新的 ETag
```

📌 浏览器会用新版本的资源替换缓存，并更新 ETag。

⚡ ETag 的优势
✅ 更精准的缓存控制：相比 Last-Modified（基于时间戳），ETag 更可靠，即使文件内容未变但时间更新了，也不会导致缓存失效。
✅ 适用于动态资源：对内容变化较频繁的资源（如 API 响应、CDN 文件），ETag 能有效减少带宽占用。
