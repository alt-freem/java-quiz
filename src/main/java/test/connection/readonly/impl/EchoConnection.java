package test.connection.readonly.impl;

public final class EchoConnection implements Connection {
    private volatile boolean closed = false;

    @Override
    public void close() {
        closed=true;
    }

    @Override
    public String query(String query) {
        if(closed) {
            throw new IllegalStateException("Connection is closed");
        }
        return query;
    }
}
