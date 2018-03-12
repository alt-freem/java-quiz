package test.design.result;

import org.junit.Assert;
import org.junit.Test;
import test.design.result.Result.MutableReference;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static test.design.result.ResultTest.Errors.*;

public class ResultTest {
    enum Errors {ERR1, ERR2, ERR3}

    private Result<Errors, String> foo(String input) {
        return input.equalsIgnoreCase("error")
                ? Result.error(ERR1)
                : Result.ok(input);
    }

    private Result<NumberFormatException, Long> parse(String s) {
        try {
            return Result.ok(Long.parseLong(s));
        } catch (NumberFormatException nfe) {
            return Result.error(nfe);
        }
    }

    private Result<ArithmeticException, Long> inc(long l) {
        return l == Long.MAX_VALUE
                ? Result.error(new ArithmeticException("value overflow"))
                : Result.ok(l + 1);
    }

    private int throwErr(String msg) throws NoSuchMethodException {
        throw new NoSuchMethodException(msg);
    }

    @Test
    public void shouldCallOnError() {
        MutableReference<Errors> err = new MutableReference<>();
        foo("error")
                .onError(err::set)
                .onSuccess($ -> fail("onSuccess handler should not be called"));
        assertEquals("should return error", ERR1, err.get());
    }

    @Test
    public void shouldExecuteChainWithSuccess() {
        MutableReference<Long> ref = new MutableReference<>();
        foo("123")
                .onError(err -> fail(err.name()))
                .then(this::parse, e -> fail("no error expected"))
                .then(this::inc, e -> fail("no error expected"))
                .onSuccess(ref::set);
        assertEquals(Long.valueOf(124), ref.get());
    }

    @Test
    public void shouldTransformError() {
        MutableReference<Errors> err = new MutableReference<>();
        foo(Long.toString(Long.MAX_VALUE))
                .onError(err::set)
                .then(this::parse, e -> ERR2)
                .then(this::inc, e -> ERR3)
                .onSuccess($ -> fail("onSuccess handler should not be called"));
        assertSame("ArithmeticException should be converted to", ERR3, err.get());

        foo("not a number")
                .onError(err::set)
                .then(this::parse, e -> ERR2)
                .then($ -> {
                    throw new AssertionError("execution chain should stopped on first error");
                }, e -> ERR3)
                .onSuccess($ -> fail("onSuccess handler should not be called"));
        assertSame("NumberFormatException should be converted to", ERR2, err.get());
    }

    @Test
    public void getShouldReturnValue() {
        long value = parse("123")
                .onErrorThrow(e -> fail("error is not expected"))
                .get();
        assertEquals(123L, value);
    }

    @Test(expected = NoSuchMethodException.class)
    public void getShouldThrowExceptionInCaseOfError() throws IOException {
        Result.ofThrowable(() -> throwErr("err"))
                .onErrorThrow(IOException.class)
                .get();
    }

    private <E> E fail(String message) {
        Assert.fail(message);
        return null;
    }

    private <E extends Throwable> void rethrow(E e) throws E {
        throw e;
    }
}