# Key Features of Spring Boot:

- Spring Boot is a framework designed to simplify the setup and development of Spring-based applications, particularly for microservices and web apps.
- It abstracts much of the complexity involved in configuring a Spring application and provides sensible defaults and embedded servers, which makes it easy to get started quickly.

- Auto-configuration: Automatically configures components based on the dependencies you add.
- Embedded Servers: No need for external servers like Tomcat or Jetty, as Spring Boot comes with an embedded web server (e.g., Tomcat, Jetty, or Undertow).
- Production-ready features: Includes monitoring, metrics, and health checks.
- Standalone applications: Spring Boot applications can run as standalone Java applications with java -jar (without needing a separate application server).
- Spring Boot Starters: Pre-configured templates for various functionalities, such as web, data access, and messaging.

Example: A simple Spring Boot REST API:

```java
@SpringBootApplication
@RestController
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}

```

# 2. What are Servlets?

- A Servlet is a Java class that is used to handle HTTP requests and generate dynamic web responses.
- Servlets are part of the Java EE (Jakarta EE) specification, and they provide low-level control over the HTTP request-response lifecycle.
- They are typically used in web servers or application servers such as Apache Tomcat, Jetty, and WildFly.

Key Features of Servlets:

- Request-Response Handling:
  A Servlet receives HTTP requests, processes them, and sends back HTTP responses.

- Low-level control:
  You can handle various aspects of request processing, such as authentication, cookies, session management, etc.

- Lifecycle Management:
  Servlets have a well-defined lifecycle (init(), service(), destroy()).

- Requires an external server:
  Unlike Spring Boot, Servlets need to be deployed to a servlet container (e.g., Tomcat).

Example: A simple Servlet handling GET requests:

```java
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.getWriter().write("Hello, World!");
    }
}
```

This would require deploying it in a servlet container like Tomcat.

这个时候想说，代码和工程的美妙，就在于它既是精密的计算，无数个零和一组成的字节在冰冷的机器里无限地流动，编码背后承载着人类的感情；而从更宏观的视角来看，一个系统又是有生命的，各个有机体相互耦合、协作，如血液之余骨骼，数据在管道中流淌，像构建任何一个工程一样一层一层构建出的软件，构成了数字时代人类生活和呼吸的分毫。
