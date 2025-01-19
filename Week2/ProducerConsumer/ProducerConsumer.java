package Week2.ProducerConsumer;

import java.util.LinkedList;

/* 
* A producer-consumer system where the consumer must wait until the buffer has data to consume.
* Java guard with wait() and notify().
*/

public class ProducerConsumer {
    private final LinkedList<Integer> buffer = new LinkedList<>();
    private final int CAPACITY = 5;

    public synchronized void produce(int value) throws InterruptedException {
        while (buffer.size() == CAPACITY) {
            wait(); // Guard: wait until the buffer is not full
        }
        buffer.add(value);
        System.out.println("Produced: " + value);
        notifyAll(); // Guard: notify waiting consumers
    }

    public synchronized void consume() throws InterruptedException {
        while (buffer.isEmpty()) {
            wait(); // Guard: wait until buffer has data
        }
        int value = buffer.removeFirst();
        System.out.println("Consumed: " + value);
        notifyAll(); // Notify waiting producers
    }

    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();
        Thread producer = new Thread(() -> {
            int value = 0;
            try {
                while (true) {
                    pc.produce(value++);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    pc.consume();
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        consumer.start();
    }

}
