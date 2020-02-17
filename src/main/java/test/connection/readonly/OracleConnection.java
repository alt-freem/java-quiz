package test.connection.readonly;

public final class OracleConnection implements Connection {
    private boolean closed = false;

    @Override
    public void close() {
        closed = true;
    }

    @Override
    public Object query(String query) {
        if (closed) {
            throw new IllegalStateException("Connection is closed");
        }
        return query;
    }
}
