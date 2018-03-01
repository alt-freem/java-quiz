package test.design.result;


import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public final class Result<ERR, RES> {
    private final boolean isError;

    public static <ERR, RES> Result<ERR, RES> error(ERR err) {
        return new Result<>(err, true);
    }

    public static <ERR, RES> Result<ERR, RES> ok(RES res) {
        return new Result<>(res, false);
    }

    private final Object result;

    private Result(Object res, boolean isError) {
        this.result = res;
        this.isError = isError;
    }

    public Safe<ERR, RES> onError(Consumer<ERR> c) {
        if (isError) {
            c.accept((ERR) result);
        }
        return new SafeResult();
    }

    public <Ex extends java.lang.Throwable> Get<?, RES> onErrorThrow(Throwable<Ex> t) throws Ex {
        if (isError) {
            t.doThrow();
            throw new IllegalStateException("ERROR should be thrown");
        } else
            return new SafeResult();
    }

    public Get<ERR, RES> onErrorThrow() throws Exception {
        if (isError)
            throw new Exception();
        else
            return new SafeResult();
    }

    public <NEXT> Result<ERR, NEXT> then(Function<RES, Result<ERR, NEXT>> next) {
        if (isError)
            return error((ERR) result);
        else
            return next.apply((RES) result);
    }

    public interface Safe<ERR, RES> {
        void onSuccess(Consumer<RES> c);

        <MRES> Result<ERR, MRES> then(Function<RES, Result<ERR, MRES>> f);
    }

    public interface Get<ERR, RES> extends Safe<ERR, RES> {
        RES get();
    }

    @FunctionalInterface
    public interface Throwable<Ex extends java.lang.Throwable> {
        void doThrow() throws Ex;
    }

    private class SafeResult implements Safe<ERR, RES>, Get<ERR, RES> {
        @Override
        public RES get() {
            return (RES) result;
        }

        @Override
        public void onSuccess(Consumer<RES> c) {
            if (!isError)
                c.accept((RES) result);
        }

        @Override
        public <NEXT> Result<ERR, NEXT> then(Function<RES, Result<ERR, NEXT>> next) {
            if (isError)
                return error((ERR) result);
             else
                return next.apply((RES) result);

        }
    }
}
