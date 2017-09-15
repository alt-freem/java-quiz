package test.concurrency;

public class ThreadStart {
    static class MutableInteger {
        int i;
    }

    public static void main(String[] args) {
        final MutableInteger ref = new MutableInteger();
        ref.i = 1;
        new Thread(() -> System.out.println(ref.i), "T2").start();
        ref.i = 2;
    }
}
