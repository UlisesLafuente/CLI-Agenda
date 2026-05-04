package com.itacademy.cliagenda.event.repository;

import com.itacademy.cliagenda.common.exception.DatabaseException;
import com.itacademy.cliagenda.common.exception.EntityNotFoundException;
import com.itacademy.cliagenda.event.model.Event;
import com.itacademy.cliagenda.infrastructure.sql.SqlConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository para operaciones CRUD de eventos en la base de datos.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class EventRepository implements IEventRepository {

    public List<Event> findAll() {
        List<Event> events = new ArrayList<>();
        String query = "SELECT id, title, description, eventDate, recurrent, annualRecurring, recurrenceInterval FROM events";

        try (Connection conn = SqlConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                LocalDateTime eventDate = rs.getTimestamp("eventDate").toLocalDateTime();
                boolean recurrent = rs.getBoolean("recurrent");
                boolean annualRecurring = rs.getBoolean("annualRecurring");
                int recurrenceInterval = rs.getInt("recurrenceInterval");

                events.add(new Event(id, title, description, eventDate, recurrent, annualRecurring, recurrenceInterval));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving events from database", e);
        }
        return events;
    }

    public Event findById(int id) {
        String query = "SELECT id, title, description, eventDate, recurrent, annualRecurring, recurrenceInterval FROM events WHERE id = ?";

        try (Connection conn = SqlConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Event(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getTimestamp("eventDate").toLocalDateTime(),
                        rs.getBoolean("recurrent"),
                        rs.getBoolean("annualRecurring"),
                        rs.getInt("recurrenceInterval")
                );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving event from database", e);
        }
        throw new EntityNotFoundException("Event", id);
    }

    public int save(Event event) {
        String query = "INSERT INTO events (title, description, eventDate, recurrent, annualRecurring, recurrenceInterval) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = SqlConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, event.getTitle());
            pstmt.setString(2, event.getDescription());
            pstmt.setTimestamp(3, Timestamp.valueOf(event.getDateTimeEvent()));
            pstmt.setBoolean(4, event.isRecurring());
            pstmt.setBoolean(5, event.isAnnualRecurring());
            pstmt.setInt(6, event.getRecurrenceInterval());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new DatabaseException("Failed to retrieve generated ID");
        } catch (SQLException e) {
            throw new DatabaseException("Error inserting event into database", e);
        }
    }

    public void update(Event event) {
        String query = "UPDATE events SET title = ?, description = ?, eventDate = ?, recurrent = ?, annualRecurring = ?, recurrenceInterval = ? WHERE id = ?";

        try (Connection conn = SqlConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, event.getTitle());
            pstmt.setString(2, event.getDescription());
            pstmt.setTimestamp(3, Timestamp.valueOf(event.getDateTimeEvent()));
            pstmt.setBoolean(4, event.isRecurring());
            pstmt.setBoolean(5, event.isAnnualRecurring());
            pstmt.setInt(6, event.getRecurrenceInterval());
            pstmt.setInt(7, event.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error updating event in database", e);
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM events WHERE id = ?";

        try (Connection conn = SqlConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting event from database", e);
        }
    }
}