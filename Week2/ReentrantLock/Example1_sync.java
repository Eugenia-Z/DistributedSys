package Week2.ReentrantLock;

public class Example1_sync {
    public synchronized void methodA() {
        System.out.println("Inside method A");
        methodB(); // 线程可以在持有锁的情况下再次进入method B
    }

    public synchronized void methodB() {
        System.out.println("Inside method B");
    }

    public static void main(String[] args) {
        Example1_sync example = new Example1_sync();
        example.methodA();
    }
}
