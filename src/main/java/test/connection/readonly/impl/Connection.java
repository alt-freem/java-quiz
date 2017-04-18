package test.connection.readonly.impl;

public interface Connection extends AutoCloseable {
     void close();

     Object query(String query);
}
