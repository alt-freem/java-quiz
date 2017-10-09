package test.connection;

import org.junit.Test;
import test.connection.readonly.impl.Connection;
import test.connection.readonly.impl.DataSource;
import test.connection.readonly.impl.EchoDataSource;

import static junit.framework.Assert.assertEquals;

public class PoolingDataSourceTest {
    private DataSource ds = new PoolingDataSource(new EchoDataSource());

    @Test
    public void reuseConnection() throws Exception {
        assertEquals("test1", executeQuery("test1"));
        assertEquals("test2", executeQuery("test2"));
    }

    private String executeQuery(String query) {
        String res = null;
        try (Connection c = ds.getConnection()) {
            res = String.valueOf(c.query(query));
        }
        return res;
    }
}