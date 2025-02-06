public class Philosopher implements Runnable {
    private final Object leftChopStick;
    private final Object rightChopStick;

    Philosopher(Object leftChopStick, Object rightChopStick) {
        this.leftChopStick = leftChopStick;
        this.rightChopStick = rightChopStick;
    }

    private void LogEvent(String event) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " " + event);
        Thread.sleep(1000);
    }

    public void run() {
        try {
            while (true) {
                LogEvent(": Thinking deeply");
                synchronized (leftChopStick) {
                    LogEvent(": Pick up left chopstick");
                    synchronized (rightChopStick) {
                        LogEvent(": picked up right chopstick - eating");
                        LogEvent(": Put down right chopstick");
                    }
                    LogEvent(": Put down left chopstick. Returning to deep thinking.");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
