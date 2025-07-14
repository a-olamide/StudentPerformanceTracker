package util;

/**
 * The {@code Config} class holds application-wide configuration constants.
 * <p>
 * This includes database connection details and any other static configuration values
 * that should be accessible throughout the application. Centralizing these values
 * makes it easier to manage and update configuration in one place.
 * </p>
 */
public class Config {
    /**
     * JDBC URL for the MySQL database connection.
     */
    public static final String DB_URL = "jdbc:mysql://localhost:3306/student_performance_system";

    /**
     * Username for the MySQL database connection.
     */
    public static final String DB_USER = "root";

    /**
     * Password for the MySQL database connection.
     */
    public static final String DB_PASSWORD = "";

    /**
     * Default admin username for initial user seeding.
     */
    public static final String ADMIN_USER = "admin";

    /**
     * Default admin password for initial user seeding.
     */
    public static final String ADMIN_PASSWORD = "admin123";
}
