public class RequestCounterBad {
    private static final int NUM_THREADS = 100000;
    private int count = 0; // shared vairable for all threads

    public synchronized void inc() {
        count++;
    }

    public int getVal() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        final RequestCounterBad counter = new RequestCounterBad();
        for (int i = 0; i < NUM_THREADS; i++) {
            Runnable thread = () -> {
                counter.inc();
            };
            new Thread(thread).start();
        }
        Thread.sleep(5000); // main thread sleeps
        System.out.println("Value should be " + NUM_THREADS + " - it is: " + counter.getVal());
    }
}

// output: Value should be 100000-it is:99999
// an example of race condition

// After adding synchronized keyword on inc()
// Value should be 100000 - it is: 100000