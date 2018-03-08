package test.design.result;

import org.junit.Assert;
import org.junit.Test;
import test.design.result.Result.MutableReference;

import static org.junit.Assert.*;
import static test.design.result.ResultTest.Errors.ERR1;
import static test.design.result.ResultTest.Errors.ERR2;
import static test.design.result.ResultTest.Errors.ERR3;

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


    @Test
    public void shouldNotCallOnSuccessOnError() {
        MutableReference<Errors> err = new MutableReference<>();
        foo("error")
                .onError(err::set)
                .onSuccess($ -> fail("onSuccess handler should not be called"));
        assertEquals("should return error", ERR1, err.get());
    }

    @Test
    public void testExecutionChain() {
        MutableReference<Long> ref = new MutableReference<>();
        foo("123")
                .onError(err -> fail(err.name()))
                .then(this::parse, e -> fail("no error expected"))
                .then(this::inc, e -> fail("no error extected"))
                .onSuccess(ref::set);
        assertEquals(Long.valueOf(124), ref.get());
    }

    @Test
    public void shouldUseErrorHandlerOnChain() {
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
                .then($ -> { throw new AssertionError("execution chain should stopped on first error"); }, e -> ERR3)
                .onSuccess($ -> fail("onSuccess handler should not be called"));
        assertSame("NumberFormatException should be converted to", ERR2, err.get());
    }

    private <E> E fail(String message){
        Assert.fail(message);
        return null;
    }
}