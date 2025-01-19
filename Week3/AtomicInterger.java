import java.util.concurrent.atomic.AtomicInteger;

public class AtomicInterger {
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {
        // Create 10 threads that increment the counter
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    atomicInteger.incrementAndGet(); // Atomic increment
                }
            }).start();
        }
        // Wait for all threads to finish (simple wait)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Get the final value of counter
        System.out.println("Final Counter Values: " + atomicInteger.get());
    }
}

// Key takeaway:
// 1. Multiple threads increment the counter simultaneously using
// incrementAndGet(), which ensures atomic updates without using locks.
// 2. The final counter value will be consistent and correct, even though
// multiple threads are updating it concurrently.
