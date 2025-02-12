package org.example;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.*;
public class HttpClient {
    private static String url = "http://localhost:8080/Lab3_war_exploded/hello";

    public static void main(String[] args) {
        // create an instance of CloseableHttpClient
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create an HttpGet request
            HttpGet request = new HttpGet(url);

            // Execute the request
            try (CloseableHttpResponse response = httpClient.execute(request)){
                // Get response status
                int statusCode = response.getCode();
                if(statusCode != 200) {
                    System.err.println("Request failed:" + response.getReasonPhrase());
                }

                // Read the response entity
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String content = EntityUtils.toString(entity);
                    System.out.println(content);
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
