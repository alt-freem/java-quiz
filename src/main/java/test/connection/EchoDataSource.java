package test.connection;

/**
 * Created by SBT-Kazakov-AB on 14.04.2017.
 */
public class EchoDataSource implements DataSource {
    @Override
    public Connection getConnection() {
        return new EchoConnection();
    }
}
