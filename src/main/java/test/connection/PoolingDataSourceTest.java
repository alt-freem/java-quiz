package test.connection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import test.connection.readonly.Connection;
import test.connection.readonly.DataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PoolingDataSourceTest {
    @Mock
    private DataSource dsMock;

    private DataSource ds;

    @Before
    public void setup() {
        when(dsMock.getConnection()).thenReturn(new EchoConnection());
        ds = new PoolingDataSource(dsMock, 1);
    }

    @Test
    public void reuseConnection() throws Exception {
        Connection c1 = ds.getConnection();
        assertEquals("test1", c1.query("test1"));
        c1.close();

        Connection c2 = ds.getConnection();
        assertEquals("test2", c2.query("test2"));
        c2.close();

        Connection c3 = ds.getConnection();
        assertEquals("test3", c3.query("test3"));
        c3.close();

        assertSame(c1, c2);
        assertSame(c2, c3);
    }

    private static final class EchoConnection implements Connection {
        private boolean closed = false;

        @Override
        public void close() {
            closed = true;
        }

        @Override
        public Object query(String query) {
            if (closed) {
                throw new IllegalStateException("Connection is closed");
            }
            return query;
        }
    }
}