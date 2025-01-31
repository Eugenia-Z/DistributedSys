import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * A basic servlet skeleton with doGet and doPost methods.
 */
public class SkierServlet extends HttpServlet {
    // Handle GET requests
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/plain");
        String urlPath = req.getPathInfo();

        // Check if we have a URL path
        if (urlPath == null || urlPath.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.getWriter().write("Missing parameters");
            return;
        }

        String[] urlParts = urlPath.split("/");
        // Validate the URL path and return appropriate response status code
        if (!isUrlValid(urlParts)) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.getWriter().write("Invalid URL path");
        } else {
            res.setStatus(HttpServletResponse.SC_OK);
            // Process URL parameters (urlParts contains URL segments)
            res.getWriter().write("It works!");
            System.out.println("URL Parts: " + String.join(", ", urlParts));
        }
    }

    // Handle POST requests
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");

        // Read the POST request body (for example, JSON payload)
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            requestBody.append(line);
        }

        // Process the request body (simple validation)
        if (requestBody.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().write("{\"message\": \"Request body is empty\"}");
        } else {
            // Example: Parse and use the request body (assuming JSON for this example)
            // Here we could use a JSON library like Jackson or Gson to parse the JSON data
            res.setStatus(HttpServletResponse.SC_OK);
            res.getWriter().write("{\"message\": \"Received data: " + requestBody.toString() + "\"}");
        }
    }

    // Helper method to validate the URL path
    private boolean isUrlValid(String[] urlParts) {
        // Simple validation: Ensure URL contains specific structure
        // e.g., /1/seasons/2019/day/1/skier/123
        if (urlParts.length < 7) {
            return false;
        }

        // Additional checks can be done here, such as validating specific values
        // For example, check if parts are numeric where expected
        try {
            int seasonYear = Integer.parseInt(urlParts[3]);
            int day = Integer.parseInt(urlParts[5]);
            int skierId = Integer.parseInt(urlParts[7]);
            // Ensure season year, day, and skier ID are within valid ranges (basic
            // validation)
            if (seasonYear < 2000 || day < 1 || skierId <= 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
