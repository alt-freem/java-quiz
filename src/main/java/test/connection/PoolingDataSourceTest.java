package test.connection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import test.connection.readonly.Connection;
import test.connection.readonly.DataSource;
import test.connection.readonly.OracleConnection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PoolingDataSourceTest {
    @Mock
    private DataSource oracleDatasource;

    private DataSource pool;

    @Before
    public void setup() {
        when(oracleDatasource.getConnection()).then(invoked -> new OracleConnection());
        pool = new PoolingDataSource(oracleDatasource, 1);
    }

    @Test
    public void reuseConnection() {
        Connection c1 = pool.getConnection();
        assertEquals("test1", c1.query("test1"));
        c1.close();

        Connection c2 = pool.getConnection();
        assertEquals("test2", c2.query("test2"));
        c2.close();

        Connection c3 = pool.getConnection();
        assertEquals("test3", c3.query("test3"));
        c3.close();

        assertSame(c1, c2);
        assertSame(c2, c3);
    }

}