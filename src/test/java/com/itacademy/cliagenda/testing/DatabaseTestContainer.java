package com.itacademy.cliagenda.testing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTestContainer {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3307/agenda_db?serverTimezone=UTC";
    private static final String USERNAME = "test";
    private static final String PASSWORD = "test";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    public static void initDatabase() throws Exception {
        String initScript = 
            "CREATE TABLE IF NOT EXISTS events (" +
            "    id INT PRIMARY KEY AUTO_INCREMENT, " +
            "    title VARCHAR(100), " +
            "    description VARCHAR(250), " +
            "    eventDate DATETIME, " +
            "    recurrent TINYINT, " +
            "    annualRecurring TINYINT, " +
            "    recurrenceInterval INT" +
            "); " +
            "CREATE TABLE IF NOT EXISTS tasks (" +
            "    id INT PRIMARY KEY AUTO_INCREMENT, " +
            "    body VARCHAR(100), " +
            "    event_fk INT, " +
            "    completed TINYINT DEFAULT 0" +
            "); " +
            "CREATE TABLE IF NOT EXISTS notes (" +
            "    id INT PRIMARY KEY AUTO_INCREMENT, " +
            "    body VARCHAR(250), " +
            "    task_fk INT" +
            ");";
        
        try (Connection conn = getConnection(); 
             Statement stmt = conn.createStatement()) {
            stmt.execute(initScript);
        }
    }

    public static void clearTables() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            stmt.execute("DELETE FROM notes");
            stmt.execute("DELETE FROM tasks");
            stmt.execute("DELETE FROM events");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
        }
    }
}