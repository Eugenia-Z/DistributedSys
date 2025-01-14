import java.util.concurrent.atomic.AtomicInteger;

public class CompareAndSwap {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(10);

        // Try to update the value from 10 to 20 if the current value is 10
        boolean success = atomicInteger.compareAndSet(10, 20);
        System.out.println("Update successful: " + success); // Output: true
        System.out.println("Current value: " + atomicInteger.get()); // Output: 20
    }
}
// This approach avoids race conditions because the update only happens if the value hasn't changed during the operation.