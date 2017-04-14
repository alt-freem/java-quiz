package test.connection;

/**
 * Created by SBT-Kazakov-AB on 14.04.2017.
 */
public class EchoConnection implements Connection {
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
