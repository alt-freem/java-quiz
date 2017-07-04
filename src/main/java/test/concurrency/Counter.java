package test.concurrency;

/**
 * Created by freem on 14.04.2017.
 */
public class Counter {
    private volatile long count = 0;

    public long increment() {
        return count++;
    }

    public long count() {
        return count;
    }
}
