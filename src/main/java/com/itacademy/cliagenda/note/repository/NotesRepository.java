package com.itacademy.cliagenda.note.repository;

import com.itacademy.cliagenda.common.exception.DatabaseException;
import com.itacademy.cliagenda.common.exception.EntityNotFoundException;
import com.itacademy.cliagenda.infrastructure.sql.SqlConnection;
import com.itacademy.cliagenda.note.model.Note;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository para operaciones CRUD de notas en la base de datos.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class NotesRepository implements INotesRepository {

    public List<Note> findAll() {
        List<Note> notes = new ArrayList<>();
        String query = "SELECT id, body, task_fk FROM notes";

        try (Connection conn = SqlConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String body = rs.getString("body");
                int task_fk = rs.getInt("task_fk");

                notes.add(new Note(id, body, task_fk));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving notes from database", e);
        }
        return notes;
    }

    public Note findById(int id) {
        String query = "SELECT id, body, task_fk FROM notes WHERE id = ?";

        try (Connection conn = SqlConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Note(
                        rs.getInt("id"),
                        rs.getString("body"),
                        rs.getInt("task_fk")
                );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving note from database", e);
        }
        throw new EntityNotFoundException("Note", id);
    }

    public void save(Note note) {
        String query = "INSERT INTO notes (id, body, task_fk) VALUES (?, ?, ?)";

        try (Connection conn = SqlConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, note.getId());
            pstmt.setString(2, note.getBody());
            pstmt.setInt(3, note.getTask_fk());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error inserting note into database", e);
        }
    }

    public void update(Note note) {
        String query = "UPDATE notes SET body = ?, task_fk = ? WHERE id = ?";

        try (Connection conn = SqlConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, note.getBody());
            pstmt.setInt(2, note.getTask_fk());
            pstmt.setInt(3, note.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error updating note in database", e);
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM notes WHERE id = ?";

        try (Connection conn = SqlConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting note from database", e);
        }
    }

    public List<Note> findByTaskId(int taskId) {
        return findAll().stream()
                .filter(note -> note.getTask_fk() == taskId)
                .toList();
    }
}