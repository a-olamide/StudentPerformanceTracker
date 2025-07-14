package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = Config.DB_URL;
    private static final String USER = Config.DB_USER;
    private static final String PASSWORD = Config.DB_PASSWORD;

    private static Connection connection;

    private DatabaseConnection() {} // private constructor

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}
