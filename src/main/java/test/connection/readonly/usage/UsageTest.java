package test.connection.readonly.usage;

import org.junit.Test;
import test.connection.PoolingDataSource;
import test.connection.readonly.impl.EchoDataSource;

import static junit.framework.Assert.assertEquals;

public class UsageTest {
    private Usage usage = new Usage(new PoolingDataSource(new EchoDataSource()));

    @Test
    public void getInfo() throws Exception {
        assertEquals("test1", usage.getInfo("test1"));
        assertEquals("test2", usage.getInfo("test2"));
    }

}