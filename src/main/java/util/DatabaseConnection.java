package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/studentperformancesystem";
    private static final String USER = "ola_dev";
    private static final String PASSWORD = "system";

    private static Connection connection;

    private DatabaseConnection() {} // private constructor

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}
