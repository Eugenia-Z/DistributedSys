package Week2.DiningPhilosopherProblem;

// 资源分配策略：奇偶编号哲学家拿筷子的顺序不同
// 奇数编号哲学家先拿左边筷子，再拿右边筷子
// 偶数编号哲学家先拿右边筷子，再拿左边筷子

class Phhilosopher extends Thread {
    private int id;
    private Object leftChopstick;
    private Object rightChopstick;

    public Phhilosopher(int id, Object leftChopstick, Object rightChopstick) {
        this.id = id;
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
    }

    @Override
    public void run() {
        try {
            while (true) {
                think();
                synchronized (id % 2 == 0 ? leftChopstick : rightChopstick) {
                    synchronized (id % 2 == 0 ? rightChopstick : leftChopstick) {
                        eat();
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking.");
        Thread.sleep((long) (Math.random() * 100));
    }

    private void eat() throws InterruptedException {
        System.out.println("Philosopher " + id + " is eating.");
        Thread.sleep((long) (Math.random() * 100));
    }

}

public class DiningPhilosophers_IdParity {
    public static void main(String[] args) {
        int NUM_PHIPOSOPHERS = 5;
        Object[] choosticks = new Object[NUM_PHIPOSOPHERS];
        for (int i = 0; i < NUM_PHIPOSOPHERS; i++) {
            choosticks[i] = new Object();
        }

        Phhilosopher[] phhilosophers = new Phhilosopher[NUM_PHIPOSOPHERS];
        for (int i = 0; i < NUM_PHIPOSOPHERS; i++) {
            Object leftChopstick = choosticks[i];
            Object rightChopstick = choosticks[(i + 1) % NUM_PHIPOSOPHERS];
            phhilosophers[i] = new Phhilosopher(i, leftChopstick, rightChopstick);
            phhilosophers[i].start();
        }
    }
}
