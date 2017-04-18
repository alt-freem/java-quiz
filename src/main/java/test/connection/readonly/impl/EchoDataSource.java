package test.connection.readonly.impl;

public final class EchoDataSource implements DataSource {
    @Override
    public Connection getConnection() {
        return new EchoConnection();
    }
}
