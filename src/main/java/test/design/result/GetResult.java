package test.design.result;

import java.util.function.Consumer;
import java.util.function.Function;

import static test.design.result.Result.exception;
import static test.design.result.Result.sneakyThrow;

class GetResult<ERR extends Throwable, RES> implements Result.Get<ERR, RES> {
    private final RES value;

    GetResult(RES value) {
        this.value = value;
    }

    @Override
    public RES get() {
        return value;
    }

    public void onSuccess(Consumer<RES> c) {
        c.accept(value);
    }

    @Override
    public <NEXT> Result.Get<ERR, NEXT> then(Function<RES, Result<ERR, NEXT>> next) {
        try {
            return next.apply(value)
                    .onErrorThrow(Result::sneakyThrow);
        } catch (Throwable err) {
            sneakyThrow(err);
            throw exception(err); // will never be thrown
        }
    }
}
