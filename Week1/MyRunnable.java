public class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new MyRunnable());
        Thread t2 = new Thread(new MyRunnable());
        t1.start(); // when t1 is started, it should execute the run() method defined in the
                    // MyRunnable (in a separate thread.)
        t2.start();
    }
}

// output:
// two threads in total, execute concurrently, not sequentially
// Thread-1 0
// Thread-0 0
// Thread-1 1
// Thread-0 1
// Thread-0 2
// Thread-0 3
// Thread-1 2
// Thread-0 4
// Thread-1 3
// Thread-0 5
// Thread-1 4
// Thread-0 6
// Thread-1 5
// Thread-0 7
// Thread-1 6
// Thread-0 8
// Thread-1 7
// Thread-0 9
// Thread-1 8
// Thread-1 9