package test.connection;

/**
 * Created by SBT-Kazakov-AB on 14.04.2017.
 */
public class Usage {
    private final DataSource ds;

    public Usage(DataSource ds) {
        this.ds = ds;
    }

    public String getInfo(String query){
        try( Connection c = ds.getConnection()){
            return String.valueOf(c.query(query));
        }
    }
}
