package test.concurrency;

public class Counter {
    private volatile long count = 0;

    public long increment() {
        return count++;
    }

    public long count() {
        return count;
    }
}
