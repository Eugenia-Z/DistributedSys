public class DiningPhilosopher_OneRightFirst {
    private final static int NUM_PHIPOSOPHERS = 5;
    private final static int NUM_CHOPSTICKS = 5;

    public static void main(String[] args) {
        final Philosopher[] ph = new Philosopher[NUM_PHIPOSOPHERS];
        Object[] chopSticks = new Object[NUM_CHOPSTICKS];

        for (int i = 0; i < NUM_CHOPSTICKS; i++) {
            chopSticks[i] = new Object();
        }
        for (int i = 0; i < NUM_PHIPOSOPHERS; i++) {
            Object leftChopStick = chopSticks[i];
            Object rightChopStick = chopSticks[(i + 1) % chopSticks.length];
            if (i == NUM_PHIPOSOPHERS - 1) {
                // The last philosopher picks up the right fork first
                ph[i] = new Philosopher(rightChopStick, leftChopStick);
            } else {
                ph[i] = new Philosopher(leftChopStick, rightChopStick);
            }
            Thread th = new Thread(ph[i], "Philosopher " + (i + 1));
            th.start();
            ;
        }
    }
}
