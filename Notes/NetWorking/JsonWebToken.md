JWT 是一种 token 技术，用于实现无状态登录.

以前我们登录都是把登录信息存储在 session 中保存，而使用 token 是通过一些加密编码的算法将信息存储在凭证中，用户只需要带着凭证来，后台服务只需要对它进行解析即可。

JWT 生成的 token 主要由三部分组成:

1. 第一部分是 header 部分 主要存储 jwt 的类型和使用哪种加密算法
2. 第二部分是 payload 载荷部分，用于存储 token 中要存储的信息，比如登录后的用户信息，失效时间等等. 不过这里不适合存储敏感信息，因为头部分 和 载荷部分都是将对应的 json 内容通过 base64 编码的，有可能会被解析。
3. 第三部分是签名信息，内容是前两部分 json 拼接到一起，根据指定类型的加密算法结合后台存储的密钥进行加密后的签名字符串，用于防止 token 的内容被篡改

token 解决的是认证不是安全问题。
token 在前端存储到 localStorage 中，如果 token 被窃取了可能会用这个凭证登录我们的系统，所以使用 token 时 最好 配合 https 安全连接，尽量避免 token 被窃取。

# 基于 JWT（JSON Web Token） 的 身份认证和用户信息传递机制

# 1. 用户访问受保护的 API

- 场景：用户访问某些需要 登录后 才能操作的接口（如订单管理、个人信息修改等）。
- 拦截逻辑：
  - 请求到达网关（API Gateway），网关有一个 过滤器（Filter） 负责拦截请求。
  - 过滤器会检查请求头（Authorization）中是否携带 JWT Token。
  - Token 校验：
    - 不存在：直接返回 401 Unauthorized（未认证）。
    - 失效：如果 Token 过期（如 JWT 有 exp 过期时间），返回 401。
    - 篡改：如果 Token 被修改，校验签名失败，返回 401。

# 2. 用户登录（获取 Token）

- 前端调用登录接口：
  - 用户输入 用户名+密码 或 验证码 登录。
- 密码存储安全性：
  - 服务器端存储用户密码时，使用 MD5 + Salt（盐值） 进行加密：
    - password_hash = MD5( password + salt )
    - 作用：
      - MD5 使密码变成不可逆的哈希值。
      - Salt（随机值）防止彩虹表攻击，即即使两个人用相同密码，数据库里存的哈希值也不同。
- 密码校验：
  - 根据用户名查询数据库中的加密存储的密码。
  - 对用户输入的密码进行 MD5+Salt 计算，和数据库中的哈希值对比。
  - 匹配成功 → 生成 Token。

# 3. 生成 JWT Token

- 使用 jwtUtils 生成 Token：
  - Token 内部存储了用户 ID，用于后续的身份识别。
  - 可能还会包含：
    - 角色（role）
    - 过期时间（exp）
    - 发行者（iss）
    - 主题（sub）
- Token 结构（Base64 编码）：

```json
Header: { "alg": "HS256", "typ": "JWT" }
Payload: { "userId": 123, "role": "admin", "exp": 1710000000 }
Signature: HMAC-SHA256( header + payload, secretKey )
```

- 生成的 Token 作为凭证返回给前端。

# 4. 前端存储 Token

存放位置：

- 前端将 Token 存入 LocalStorage：

```js
localStorage.setItem("token", jwtToken);
```

每次请求时，将 Token 附加到请求头：

```js
axios.get("/user/info", {
  headers: { Authorization: `Bearer ${jwtToken}` },
});
```

- 为什么用 LocalStorage？
- 优点：
  - 持久化存储，不会因为页面刷新丢失（与 sessionStorage 相比）。
- 缺点：
  - XSS 攻击风险：如果站点有 XSS（跨站脚本漏洞），攻击者可能读取 Token。

# 5. 网关拦截和透传用户信息

- 请求再次到达网关：

  - 过滤器检查请求头中的 Token。
  - Token 解析成功：

    - 解析 userId 等信息。
    - 请求通过，并将解析出的用户信息 加入请求头，例如：

      ```pgsql
      X-User-ID: 123
      X-User-Role: admin
      ```

请求转发到用户微服务。

# 6. 用户微服务获取用户信息

- 微服务过滤器：

      - 解析请求头中的 X-User-ID。
      - 通过 ThreadLocal 机制，在当前线程中存储用户信息。
      - 为什么使用 ThreadLocal？
          - 避免参数层层传递（比如每个方法都要传 userId）。
          - ThreadLocal 绑定当前线程的用户数据，使得后续任何地方都可以通过 ThreadLocal 获取当前用户。

      - 示例：存储到 ThreadLocal

```java
public class UserContext {
private static final ThreadLocal<Integer> userThreadLocal = new ThreadLocal<>();

    public static void setUserId(Integer userId) {
        userThreadLocal.set(userId);
    }

    public static Integer getUserId() {
        return userThreadLocal.get();
    }

    public static void clear() {
        userThreadLocal.remove();
    }

}
```

在过滤器中：

```java

Integer userId = Integer.valueOf(request.getHeader("X-User-ID"));
UserContext.setUserId(userId);
```

在业务层获取用户 ID：

```java

Integer currentUserId = UserContext.getUserId();
```

# 7. 访问受保护接口

当用户访问需要身份验证的 API（如查询个人订单）：
前端携带 Token → 网关过滤器校验 → 用户微服务过滤器存入 ThreadLocal → 业务代码中可获取 UserContext.getUserId()。
避免每个方法都显式传 userId，提高代码清晰度。
