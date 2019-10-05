package test.connection;

import test.connection.readonly.Connection;
import test.connection.readonly.DataSource;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PoolingDataSource implements DataSource {

    private final BlockingQueue<Connection> queue;
    private final Map <Connection, Connection> proxyMap;

    public PoolingDataSource(DataSource dataSource, int poolSize) {

        Connection conn, proxy;
        proxyMap = new ConcurrentHashMap<>(poolSize);
        queue = new ArrayBlockingQueue<>(poolSize);

        for (int i = 0; i < poolSize; i++) {
            conn = dataSource.getConnection();
            proxy = makeProxy(conn);
            queue.offer(proxy);
            proxyMap.put(conn, proxy);
        }
    }

    @Override
    public Connection getConnection() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    private void takeBack (Connection conn) {
        queue.offer(proxyMap.get(conn));
    }

    private Connection makeProxy (Connection connection) {

        Connection proxy = (Connection) Proxy.
                newProxyInstance(
                        connection.getClass().getClassLoader(),
                        connection.getClass().getInterfaces(),
                        new ProxyInvoker(connection)
        );
        return proxy;
    }

    private final class ProxyInvoker implements InvocationHandler {
        private Connection conn = null;

        public ProxyInvoker (Connection connection) {
            this.conn = connection;
        }

        @Override
        public Object invoke(Object proxy,
                             Method method,
                             Object[] args) throws Throwable {

            if(method.getName().equals("close")) {
                takeBack (conn);
                return null;
            }
            else
                return method.invoke(conn, args) ;
        }
    }
}
