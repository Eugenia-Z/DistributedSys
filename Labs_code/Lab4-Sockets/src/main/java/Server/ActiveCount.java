package Server;

public class ActiveCount {
    private int activeThreadCount = 0;

    public synchronized void incrementCount() {
        activeThreadCount++;
    }

    public synchronized void decrementCount() {
        activeThreadCount--;
    }

    public synchronized int getCount() {
        return activeThreadCount;
    }

}

