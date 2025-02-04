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
