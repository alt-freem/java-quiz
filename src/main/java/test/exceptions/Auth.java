package test.exceptions;

public interface Auth<P> {

    void checkAuthorized(P permission) throws RuntimeException;
}
