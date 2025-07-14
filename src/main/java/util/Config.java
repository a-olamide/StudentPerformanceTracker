package util;

/**
 * Config class holds application-wide constants such as database connection details.
 * All static configuration values should be placed here for easy management and reuse.
 */
public class Config {
    /**
     * JDBC URL for the MySQL database connection.
     */
    public static final String DB_URL = "jdbc:mysql://localhost:3306/student_performance_system";

    /**
     * Username for the MySQL database connection.
     */
    public static final String DB_USER = "ola_dev";

    /**
     * Password for the MySQL database connection.
     */
    public static final String DB_PASSWORD = "system";
}
