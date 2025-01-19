package Week2.ReentrantLock;

import java.util.concurrent.locks.ReentrantLock;

public class Example2_reentrant {
    private final ReentrantLock lock = new ReentrantLock();

    public void methodA() {
        lock.lock();
        try {
            System.out.println("Inside methodA.");
            methodB();
        } finally {
            lock.unlock();
            ;
        }
    }

    public void methodB() {
        lock.lock();
        try {
            System.out.println("Inside methodB.");
        } finally {
            lock.unlock();
            ;
        }
    }

    public static void main(String[] args) {
        Example2_reentrant example = new Example2_reentrant();
        example.methodA();
    }
}

/*
 * 线程进入 methodA 后获取锁。
 * 在 methodA 内部调用 methodB 时，线程再次获取同一把锁（计数器递增）。
 * 锁的计数器在 unlock 时递减，直到释放。
 */