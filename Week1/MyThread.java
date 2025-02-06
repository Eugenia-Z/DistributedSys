public class MyThread extends Thread {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + " - " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        t1.start();
        t2.start();
    }
}

/*
 * Output:
 * (slower than using Runnable)
 * Thread-1 - 1
 * Thread-0 - 1
 * Thread-0 - 2
 * Thread-1 - 2
 * Thread-1 - 3
 * Thread-0 - 3
 * Thread-0 - 4
 * Thread-1 - 4
 * Thread-1 - 5
 * Thread-0 - 5
 */