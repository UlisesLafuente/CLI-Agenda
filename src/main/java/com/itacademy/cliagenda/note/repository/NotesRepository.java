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
                Integer task_fk = rs.getObject("task_fk", Integer.class);

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
                        rs.getObject("task_fk", Integer.class)
                );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving note from database", e);
        }
        throw new EntityNotFoundException("Note", id);
    }

    public int save(Note note) {
        String query = "INSERT INTO notes (body, task_fk) VALUES (?, ?)";

        try (Connection conn = SqlConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, note.getBody());
            if (note.getTask_fk() == null) {
                pstmt.setNull(2, Types.INTEGER);
            } else {
                pstmt.setInt(2, note.getTask_fk());
            }

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new DatabaseException("Failed to retrieve generated ID");
        } catch (SQLException e) {
            throw new DatabaseException("Error inserting note into database", e);
        }
    }

    public void update(Note note) {
        String query = "UPDATE notes SET body = ?, task_fk = ? WHERE id = ?";

        try (Connection conn = SqlConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, note.getBody());
            if (note.getTask_fk() == null) {
                pstmt.setNull(2, Types.INTEGER);
            } else {
                pstmt.setInt(2, note.getTask_fk());
            }
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
        List<Note> notes = new ArrayList<>();
        String query = "SELECT id, body, task_fk FROM notes WHERE task_fk = ?";

        try (Connection conn = SqlConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, taskId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                notes.add(new Note(
                        rs.getInt("id"),
                        rs.getString("body"),
                        rs.getObject("task_fk", Integer.class)
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving notes by task id from database", e);
        }
        return notes;
    }
}