package com.itacademy.cliagenda.note.service;

import com.itacademy.cliagenda.note.dto.CreateNoteRequest;
import com.itacademy.cliagenda.note.dto.UpdateNoteRequest;
import com.itacademy.cliagenda.note.model.Note;
import com.itacademy.cliagenda.note.repository.INotesRepository;

import java.util.List;

/**
 * Service para lógica de negocio de notas.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class NotesService {

    private final INotesRepository repo;

    public NotesService(INotesRepository repo) {
        this.repo = repo;
    }

    public Note createNote(String body) {
        CreateNoteRequest request = new CreateNoteRequest(body, null);
        Note newNote = new Note(0, request.body(), request.taskId());
        int generatedId = repo.save(newNote);
        return new Note(generatedId, request.body(), request.taskId());
    }

    public Note createNote(String body, int taskFk) {
        CreateNoteRequest request = new CreateNoteRequest(body, taskFk > 0 ? taskFk : null);
        Note newNote = new Note(0, request.body(), request.taskId());
        int generatedId = repo.save(newNote);
        return new Note(generatedId, request.body(), request.taskId());
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
}