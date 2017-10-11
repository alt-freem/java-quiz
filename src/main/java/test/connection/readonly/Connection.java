package test.connection.readonly;

public interface Connection extends AutoCloseable {
     void close();

     Object query(String query);
}
