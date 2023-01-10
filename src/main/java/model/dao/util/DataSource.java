package model.dao.util;

import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DataSource {
    private static final String DB_RESOURCE_BUNDLE = "db";
    private static final String URL = "db.url";
    private static DataSource instance = null;

    private DataSource() {
    }

    /**
     * @return only one instance of the class
     */
    public synchronized static DataSource getInstance() {
        if (instance == null)
            instance = new DataSource();
        return instance;
    }

    /**
     * @return object of data source;
     */
    private Jdbc3PoolingDataSource getPooledConnectionDataSource() {
        ResourceBundle rb = ResourceBundle.getBundle(DB_RESOURCE_BUNDLE);
        Jdbc3PoolingDataSource source = new Jdbc3PoolingDataSource();
        source.setURL(rb.getString(URL));
        return source;
    }

    /**
     * The method by which we will get the connection,
     * but not directly, but through the connection pool.
     *
     * @return connection from pool connection
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = getPooledConnectionDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
