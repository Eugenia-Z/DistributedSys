package org.example;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class RequestCountBarrier {
    private static final int NUM_THREADS = 100;
    private int count = 0;

    // Synchronized method to increment the request counter
    synchronized public void inc() {
        count++;
    }
    public int getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        RequestCountBarrier counter = new RequestCountBarrier();
        CountDownLatch latch = new CountDownLatch(NUM_THREADS);
        String url = "http://localhost:8080/Lab3_war_exploded/hello";
        // Start the timer
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < NUM_THREADS; i++) {
            Runnable thread = () -> {
                makeHttpRequest(url);
                counter.inc();
                latch.countDown();
            };
            new Thread(thread).start();
        }

        latch.await();
        // End the timer
        long endTime = System.currentTimeMillis();
        System.out.println("All requests completed.");
        System.out.println("Total HTTP requests: " + counter.getCount());
        System.out.println("Total execution time: " + (endTime - startTime) + " ms");
    }
    private static void makeHttpRequest(String url) {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getCode();
            if (statusCode != 200) {
                throw new RuntimeException("Thread " + Thread.currentThread().getId() + " request failed: " + response.getReasonPhrase());
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                EntityUtils.consume(entity); // Ensure the response is fully consumed
            }
        } catch (IOException e){
            System.err.println("Thread " + Thread.currentThread().getId() + " error: " + e.getMessage());
        }

    }
}
