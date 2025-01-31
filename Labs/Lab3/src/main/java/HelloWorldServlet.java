import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * A basic servlet skeleton with doGet and doPost methods.
 */
@WebServlet("/hello")
public class HelloWorldServlet extends HttpServlet {
    private String message;

    public void init() throws ServletException {
        message = "Hello World!";
    }

    // Handle a GET request
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // set response content type to text
        response.setContentType("text/html");

        // sleep for 1000ms.
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        response.getWriter().println(message);

        // send the response
        PrintWriter out = response.getWriter();
        out.println("<h1>" + message + "</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: Implement POST request handling logic
    }

    public void destroy() {}
}
