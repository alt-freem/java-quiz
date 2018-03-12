package test.design.result;


import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public final class Result<ERR, RES> { // TODO add separate ThrowingResult with Ex extends Throwable generic type |

    public static <ERR, RES> Result<ERR, RES> error(ERR err) {
        return new Result<>(err, true);
    }

    public static <ERR, RES> Result<ERR, RES> ok(RES res) {
        return new Result<>(res, false);
    }

    public static <Ex extends Throwable, RES> Result<Ex, RES> ofThrowable(ThrowingFunction<RES, Ex> function) {
        try {
            return new Result<>(function.call(), false);
        } catch (Throwable e) {
            return new Result<>(e, true);
        }
    }

    private final Object result;
    private final boolean isError;

    private Result(Object res, boolean isError) {
        this.result = res;
        this.isError = isError;
    }

    public Safe<ERR, RES> onError(Consumer<ERR> c) {
        if (isError) {
            c.accept((ERR) result);
        }
        return new SafeResult<>(result, c, isError);
    }

/*
    public <Ex extends Throwable> Get<Ex, RES> onErrorThrow(Class<Ex> errClass) throws Ex {
        if (isError) {
            sneakyThrow(result instanceof Throwable ? (Throwable) result : new Exception(String.valueOf(result)));
            throw exception();
        } else {
            return new GetResult<>((RES)result);
        }
    }
*/

    public <Ex extends Throwable> Get<Ex, RES> onErrorThrow(Throw<Ex> throwing) throws Ex {
        if (isError) {
            throwing.doThrow();
            throw exception(result);
        } else {
            return new GetResult<>((RES)result);
        }
    }

    public <Ex extends Throwable> Get<Ex, RES> onErrorThrow(ThrowFromErr<ERR, Ex> throwing) throws Ex {
        if (isError) {
            throwing.doThrow((ERR) result);
            throw exception(result);
        } else {
            return new GetResult<>((RES)result);
        }
    }

    static IllegalStateException exception(Object err) {
        return err instanceof Throwable
                ? new IllegalStateException("ERROR expected to be thrown", (Throwable) err)
                : new IllegalStateException("ERROR expected to be thrown: "+String.valueOf(err));
    }

    static <Ex extends Throwable> void sneakyThrow(Throwable ex) throws Ex {
        throw (Ex) ex;
    }

    public interface Safe<ERR, RES> {
        void onSuccess(Consumer<RES> c);

        <NEXT> Safe<ERR, NEXT> then(Function<RES, Result<ERR, NEXT>> next);

        <ERR2, NEXT> Safe<ERR, NEXT> then(Function<RES, Result<ERR2, NEXT>> next, Function<ERR2, ERR> errMapping);
    }

    public interface Get<ERR extends Throwable, RES> {
        RES get();

        void onSuccess(Consumer<RES> c);

        <NEXT> Get<ERR, NEXT> then(Function<RES, Result<ERR, NEXT>> next);
    }

    @FunctionalInterface
    public interface ThrowFromErr<ERR, Ex extends Throwable> {
        void doThrow(ERR err) throws Ex;
    }

    @FunctionalInterface
    public interface Throw<Ex extends Throwable> {
        void doThrow() throws Ex;
    }

    @FunctionalInterface
    public interface ThrowingFunction<R, Ex extends Throwable> {
        R call() throws Ex;
    }

    static class MutableReference<T> {
        private T value;

        void set(T value) {
            this.value = value;
        }

        T get() {
            return this.value;
        }
    }
}
