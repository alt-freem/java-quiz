package test.concurrency;

import org.junit.Test;

public class State {
    private volatile int a;
    private int b;

    void update(int a, int b) {
        this.b = b;
        this.a = a;
    }

    public String toString() {
        return "{" + a + ", " + b + '}';
    }

    @Test
    public void test() {
        final State state = new State();
        Thread t1 = new Thread(() -> state.update(1, 1), "T1");
        Thread t2 = new Thread(() -> System.out.println(state), "T2");
    }
}

