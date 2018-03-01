package test.design;

import test.design.result.Result;

import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        foo()
//                .onError(System.out::println)
                .onErrorThrow(Test::newRuntimeException)
                .onSuccess(System.out::println);

        foo()
                .onError(System.out::println)
                .then(Test::bar)
                .onErrorThrow(Test::newRuntimeException)
                .get();

        char c = foo()
                .then(Test::bar)
                .then(Test::baz)
                .onErrorThrow(Test::newRuntimeException)
                .get();
    }

    enum Errors {ERR1, ERR2}

    private static void newException() throws IOException {
        throw new IOException("ERR");
    }

    private static void newRuntimeException() {
        throw new RuntimeException("RuntimeEx");
    }

    static Result<Errors, String> foo() {
        return System.currentTimeMillis() < 0 ? Result.ok("OK") : Result.error(Errors.ERR1);
    }

    static Result<Errors, Long> bar(String s) {
        return System.currentTimeMillis() > 0 ? Result.ok(Long.parseLong(s)) : Result.error(Errors.ERR2);
    }

    static Result<Errors, Character> baz(long l) {
        return Result.ok(Long.toString(l).charAt(0));
    }
}
