package test.connection;

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
