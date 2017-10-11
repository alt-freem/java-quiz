package test.connection;

import test.connection.readonly.Connection;
import test.connection.readonly.DataSource;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PoolingDataSource implements DataSource {

    private final BlockingQueue<Connection> queue;

    public PoolingDataSource(DataSource dataSource, int poolSize) {
        queue = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            queue.offer(dataSource.getConnection());
        }
    }

    @Override
    public Connection getConnection() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
