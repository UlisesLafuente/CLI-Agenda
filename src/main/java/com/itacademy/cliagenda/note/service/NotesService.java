package com.itacademy.cliagenda.note.service;

import com.itacademy.cliagenda.common.exception.ValidationException;
import com.itacademy.cliagenda.note.model.Note;
import com.itacademy.cliagenda.note.repository.NotesRepository;
import com.itacademy.cliagenda.task.model.Task;

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
        return createNote(body, null);
    }

    public Note createNote(String body, Task task_fk) {
        validateNoteBody(body);
        int id = generateNextId();
        int taskFk = task_fk != null ? task_fk.getId() : 0;
        Note newNote = new Note(id, body, taskFk);
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
        validateNoteBody(note.getBody());
        repo.update(note);
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

    private void validateNoteBody(String body) {
        if (body == null || body.trim().isEmpty()) {
            throw new ValidationException("Note body cannot be empty");
        }
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