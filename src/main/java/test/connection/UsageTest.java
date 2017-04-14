package test.connection;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by SBT-Kazakov-AB on 14.04.2017.
 */
public class UsageTest {
    Usage usage = new Usage(new PoolingDataSource(new EchoDataSource()));

    @Test
    public void getInfo() throws Exception {
        assertEquals("test1", usage.getInfo("test1"));
        assertEquals("test2", usage.getInfo("test2"));
    }

}