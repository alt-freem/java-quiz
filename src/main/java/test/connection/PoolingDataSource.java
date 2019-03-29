package test.connection;

import test.connection.readonly.Connection;
import test.connection.readonly.DataSource;

import java.lang.reflect.Field;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PoolingDataSource implements DataSource {

    private final BlockingQueue<Connection> queue;

    private Connection connection;

    public PoolingDataSource(DataSource dataSource, int poolSize) {
        queue = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            queue.offer(dataSource.getConnection());
        }
    }

    @Override
    public Connection getConnection() {
        try {
            if (connection == null) {
                connection = queue.take();
            }
            Field closedField = connection.getClass().getDeclaredField("closed");
            closedField.setAccessible(true);
            boolean closed = (boolean) closedField.get(connection);
            if (closed) {
                closedField.set(connection,false);
            }
            return connection;
        } catch (InterruptedException | NoSuchFieldException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
