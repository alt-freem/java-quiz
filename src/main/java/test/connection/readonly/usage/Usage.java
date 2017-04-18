package test.connection.readonly.usage;

import test.connection.readonly.impl.Connection;
import test.connection.readonly.impl.DataSource;

public class Usage {
    private final DataSource ds;

    Usage(DataSource ds) {
        this.ds = ds;
    }

    String getInfo(String query) {
        try( Connection c = ds.getConnection()){
            return String.valueOf(c.query(query));
        }
    }
}
