package test.connection;

import test.connection.readonly.impl.Connection;
import test.connection.readonly.impl.DataSource;

/**
 * Created by SBT-Kazakov-AB on 14.04.2017.
 */
public class PoolingDataSource implements DataSource {
    private final Connection connection;

    public PoolingDataSource(DataSource dataSource) {
        this.connection = dataSource.getConnection();
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
