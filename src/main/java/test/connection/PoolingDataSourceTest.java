package test.connection;

import org.junit.Test;
import test.connection.readonly.impl.Connection;
import test.connection.readonly.impl.DataSource;
import test.connection.readonly.impl.EchoDataSource;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;

public class PoolingDataSourceTest {
    private DataSource ds = new PoolingDataSource(new EchoDataSource(), 1);

    @Test
    public void reuseConnection() throws Exception {
        Connection c1 = ds.getConnection();
        assertEquals("test1", c1.query("test1"));
        c1.close();

        Connection c2 = ds.getConnection();
        assertSame(c1, c2);
        assertEquals("test2", c2.query("test2"));
        c2.close();
    }
}