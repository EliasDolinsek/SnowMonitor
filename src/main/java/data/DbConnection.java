package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    // Defaults
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "";

    private static final String DEFAULT_SERVER = "localhost"; // IP-address
    private static final String DEFAULT_PORT = "3306"; // port number
    private static final String DEFAULT_SCHEMA = "snow_monitor"; // schema name

    private static Connection connection; // database connection

    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // connect to the database
            String url = String.format("%s:%s/%s", DEFAULT_SERVER, DEFAULT_PORT, DEFAULT_SCHEMA);
            String fullURL = String.format("jdbc:mysql://%s?useSSL=false", url);
            connection = DriverManager.getConnection(fullURL, DEFAULT_USER, DEFAULT_PASSWORD);
        }
        return connection;
    }

    public static void close() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }
}