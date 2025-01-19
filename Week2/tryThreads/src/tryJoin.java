class MyThread extends Thread {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("Thread: " + i);
            try {
                Thread.sleep(500); // Simulate work
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted.");
            }
        }
    }
}

public class tryJoin {
    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();

        try {
            thread.join(); // Main thread waits for MyThread to finish
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        System.out.println("Main thread resumes after MyThread finishes.");
    }
}
