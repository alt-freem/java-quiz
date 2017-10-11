package test.connection;

import test.connection.readonly.Connection;
import test.connection.readonly.DataSource;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class PoolingDataSource implements DataSource {

    private final BlockingDeque<Connection> queue;

    public PoolingDataSource(DataSource dataSource, int poolSize) {
        queue = new LinkedBlockingDeque<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            queue.offerFirst(dataSource.getConnection());
        }
    }

    @Override
    public Connection getConnection() {
        try {
            return queue.takeFirst();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
