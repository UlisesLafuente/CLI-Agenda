package com.itacademy.cliagenda.note.service;

import com.itacademy.cliagenda.note.dto.CreateNoteRequest;
import com.itacademy.cliagenda.note.dto.UpdateNoteRequest;
import com.itacademy.cliagenda.note.model.Note;
import com.itacademy.cliagenda.note.repository.NotesRepository;

import java.util.List;

/**
 * Service para lógica de negocio de notas.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class NotesService {

    private final NotesRepository repo;

    public NotesService(NotesRepository repo) {
        this.repo = repo;
    }

    public Note createNote(String body) {
        CreateNoteRequest request = new CreateNoteRequest(body, null);
        int id = generateNextId();
        Note newNote = new Note(id, request.body(), request.taskId() != null ? request.taskId() : 0);
        repo.save(newNote);
        return newNote;
    }

    public Note createNote(String body, int taskFk) {
        CreateNoteRequest request = new CreateNoteRequest(body, taskFk > 0 ? taskFk : null);
        int id = generateNextId();
        Note newNote = new Note(id, request.body(), request.taskId() != null ? request.taskId() : 0);
        repo.save(newNote);
        return newNote;
    }

    public List<Note> getAllNotes() {
        return repo.findAll();
    }

    public Note findNoteById(int id) {
        return repo.findById(id);
    }

    public void deleteNoteById(int id) {
        repo.delete(id);
    }

    public void updateNote(Note note) {
        UpdateNoteRequest request = new UpdateNoteRequest(note.getBody(), note.getTask_fk());
        Note existingNote = repo.findById(note.getId());

        if (request.body() != null && !request.body().isEmpty()) {
            existingNote.changeBody(request.body());
        }
        if (request.taskId() != null) {
            existingNote.setTask_fk(request.taskId());
        }

        repo.update(existingNote);
    }

    public List<Note> getNotesByTaskId(int taskId) {
        return repo.findByTaskId(taskId);
    }

    public String formatNoteList(List<Note> notes) {
        if (notes == null || notes.isEmpty()) {
            return "No notes found";
        }
        StringBuilder sb = new StringBuilder();
        for (Note note : notes) {
            sb.append("ID: ").append(note.getId())
                    .append(" | ").append(note.getBody())
                    .append("\n");
        }
        return sb.toString();
    }

    public String formatNoteDetail(Note note) {
        if (note == null) {
            return "Note not found";
        }
        return "ID: " + note.getId() + "\n" +
                "Body: " + note.getBody() + "\n" +
                "Task FK: " + note.getTask_fk() + "\n";
    }

    private int generateNextId() {
        List<Note> notes = repo.findAll();
        int maxId = notes.stream()
                .mapToInt(Note::getId)
                .max()
                .orElse(0);
        return maxId + 1;
    }
}