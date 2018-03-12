package test.design.result;

import javafx.util.Pair;

import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("unchecked")
class SafeResult<ERR, RES> implements Result.Safe<ERR, RES> {
    private final Object errorOrValue;
    private final Consumer<ERR> errHandler;
    private final boolean isError;

    SafeResult(Object errorOrValue, Consumer<ERR> errHandler, boolean isError) {
        this.errorOrValue = errorOrValue;
        this.errHandler = errHandler;
        this.isError = isError;
    }

    @Override
    public void onSuccess(Consumer<RES> c) {
        if (!isError)
            c.accept((RES) errorOrValue);
    }

    @Override
    public <NEXT> Result.Safe<ERR, NEXT> then(Function<RES, Result<ERR, NEXT>> next) {
        if (isError) {
            return Result.<ERR, NEXT>error((ERR) errorOrValue)
                    .onError(errHandler);
        } else {
            return next.apply((RES) errorOrValue)
                    .onError(errHandler);
        }
    }

    @Override
    public <ERR2, NEXT> Result.Safe<ERR, NEXT> then(Function<RES, Result<ERR2, NEXT>> next, Function<ERR2, ERR> errMapping) {
        if (isError) {
            return Result.<ERR, NEXT>error((ERR) errorOrValue)
                    .onError(errHandler);
        } else {
            Result.MutableReference<Pair<Boolean, Object>> res = new Result.MutableReference<>();
            next.apply((RES) errorOrValue)
                    .onError(e -> res.set(new Pair<>(true, e)))
                    .onSuccess(r -> res.set(new Pair<>(false, r)));

            boolean isError = res.get().getKey();
            if (isError) {
                ERR2 err = (ERR2) res.get().getValue();
                return Result.<ERR, NEXT>error(errMapping.apply(err))
                        .onError(errHandler);
            } else {
                return Result.<ERR, NEXT>ok((NEXT) res.get().getValue())
                        .onError(errHandler);
            }
        }
    }
}
