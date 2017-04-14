package test.connection;

/**
 * Created by SBT-Kazakov-AB on 14.04.2017.
 */
public interface Connection extends AutoCloseable {
     void close();

     Object query(String query);
}
