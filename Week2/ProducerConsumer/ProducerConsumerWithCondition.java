package Week2.ProducerConsumer;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerWithCondition {
    private final LinkedList<Integer> buffer = new LinkedList<>();
    private final int CAPACITY = 5;
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public void produce(int value) throws InterruptedException {
        lock.lock();
        try {
            while (buffer.size() == CAPACITY) { // Guard
                notFull.await(); // Wait until buffer has space
            }
            buffer.add(value);
            System.out.println("Produced: " + value);
            notEmpty.signalAll(); // Notify waiting consumers
        } finally {
            lock.unlock();
        }
    }

    public void consume() throws InterruptedException {
        lock.lock();
        try {
            while (buffer.isEmpty()) { // Guard
                notEmpty.await(); // Wait until buffer has data
            }
            int value = buffer.removeFirst();
            System.out.println("Consumed: " + value);
            notFull.signalAll(); // Notify waiting producers
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ProducerConsumerWithCondition pc = new ProducerConsumerWithCondition();

        Thread producer = new Thread(() -> {
            int value = 0;
            try {
                while (true) {
                    pc.produce(value++);
                    Thread.sleep(500); // Simulate work
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    pc.consume();
                    Thread.sleep(1000); // Simulate work
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }
}
