package com.itacademy.cliagenda.task.repository;

import com.itacademy.cliagenda.common.exception.DatabaseException;
import com.itacademy.cliagenda.common.exception.EntityNotFoundException;
import com.itacademy.cliagenda.infrastructure.sql.SqlConnection;
import com.itacademy.cliagenda.task.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository para operaciones CRUD de tareas en la base de datos.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class TaskRepository implements ITaskRepository {

    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT id, body, event_fk, completed FROM tasks";

        try (Connection conn = SqlConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String body = rs.getString("body");
                Integer event_fk = rs.getObject("event_fk", Integer.class);
                boolean completed = rs.getBoolean("completed");

                tasks.add(new Task(id, body, event_fk, completed));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving tasks from database", e);
        }
        return tasks;
    }

    public Task findById(int id) {
        String query = "SELECT id, body, event_fk, completed FROM tasks WHERE id = ?";

        try (Connection conn = SqlConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Task(
                        rs.getInt("id"),
                        rs.getString("body"),
                        rs.getObject("event_fk", Integer.class),
                        rs.getBoolean("completed")
                );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving task from database", e);
        }
        throw new EntityNotFoundException("Task", id);
    }

    public int save(Task task) {
        String query = "INSERT INTO tasks (body, event_fk, completed) VALUES (?, ?, ?)";

        try (Connection conn = SqlConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, task.getBody());
            if (task.getEvent_fk() == null) {
                pstmt.setNull(2, Types.INTEGER);
            } else {
                pstmt.setInt(2, task.getEvent_fk());
            }
            pstmt.setBoolean(3, task.isCompleted());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new DatabaseException("Failed to retrieve generated ID");
        } catch (SQLException e) {
            throw new DatabaseException("Error inserting task into database", e);
        }
    }

    public void update(Task task) {
        String query = "UPDATE tasks SET body = ?, event_fk = ?, completed = ? WHERE id = ?";

        try (Connection conn = SqlConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, task.getBody());
            if (task.getEvent_fk() == null) {
                pstmt.setNull(2, Types.INTEGER);
            } else {
                pstmt.setInt(2, task.getEvent_fk());
            }
            pstmt.setBoolean(3, task.isCompleted());
            pstmt.setInt(4, task.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error updating task in database", e);
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = SqlConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting task from database", e);
        }
    }

    public List<Task> findByEventId(int eventId) {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT id, body, event_fk, completed FROM tasks WHERE event_fk = ?";

        try (Connection conn = SqlConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt("id"),
                        rs.getString("body"),
                        rs.getObject("event_fk", Integer.class),
                        rs.getBoolean("completed")
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving tasks by event id from database", e);
        }
        return tasks;
    }

    public List<Task> findByCompleted(boolean completed) {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT id, body, event_fk, completed FROM tasks WHERE completed = ?";

        try (Connection conn = SqlConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setBoolean(1, completed);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt("id"),
                        rs.getString("body"),
                        rs.getObject("event_fk", Integer.class),
                        rs.getBoolean("completed")
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving tasks by completed status from database", e);
        }
        return tasks;
    }
}