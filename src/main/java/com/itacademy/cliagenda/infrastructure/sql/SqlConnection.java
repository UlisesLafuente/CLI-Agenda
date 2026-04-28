package com.itacademy.cliagenda.infrastructure.sql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Singleton que gestiona la conexión a la base de datos SQL.
 * <p>
 * Proporciona una única conexión reutilizable durante toda la aplicación,
 * evitando la creación de múltiples conexiones.
 * </p>
 * <p>
 * Uso:
 * <pre>
 *     Connection conn = SqlConnection.getInstance().getConnection();
 *     // usar conn para consultas...
 *     // al final de la app:
 *     SqlConnection.getInstance().closeConnection();
 * </pre>
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */

public class SqlConnection {
    private static volatile SqlConnection instance;
    private Connection connection;
    private Properties props;

    private SqlConnection() {
        loadProperties();
    }

    public static SqlConnection getInstance() {
        if (instance == null) {
            synchronized (SqlConnection.class) {
                if (instance == null) {
                    instance = new SqlConnection();
                }
            }
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    public synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(
                    props.getProperty("jdbc.url"),
                    props.getProperty("jdbc.username"),
                    props.getProperty("jdbc.password")
            );
        }
        return connection;
    }

    public synchronized void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error closing connection", e);
            }
        }
    }

    private void loadProperties() {
        this.props = new Properties();

        String testUrl = System.getProperty("jdbc.url");
        String testUser = System.getProperty("jdbc.username");
        String testPass = System.getProperty("jdbc.password");

        if (testUrl != null) {
            props.setProperty("jdbc.url", testUrl);
            props.setProperty("jdbc.username", testUser != null ? testUser : "");
            props.setProperty("jdbc.password", testPass != null ? testPass : "");
            return;
        }

        InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties");
        if (input == null) {
            input = getClass().getClassLoader().getResourceAsStream("com/itacademy/cliagenda/application/config/application.properties");
        }
        if (input == null) {
            throw new RuntimeException("Could not find application.properties");
        }
        try {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties", e);
        } finally {
            try {
                input.close();
            } catch (IOException ignored) {
            }
        }
    }
}
