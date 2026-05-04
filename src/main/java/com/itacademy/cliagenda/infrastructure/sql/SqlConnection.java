package com.itacademy.cliagenda.infrastructure.sql;

import com.itacademy.cliagenda.infrastructure.config.ConfigLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    private final ConfigLoader config;

    private SqlConnection() {
        this.config = new ConfigLoader();
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
                    config.getUrl(),
                    config.getUsername(),
                    config.getPassword()
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
}
