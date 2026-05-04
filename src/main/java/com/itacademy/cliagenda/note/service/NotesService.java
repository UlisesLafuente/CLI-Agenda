package com.itacademy.cliagenda.note.service;

import com.itacademy.cliagenda.common.validator.NoteValidator;
import com.itacademy.cliagenda.note.dto.CreateNoteRequest;
import com.itacademy.cliagenda.note.dto.UpdateNoteRequest;
import com.itacademy.cliagenda.note.model.Note;
import com.itacademy.cliagenda.note.repository.INotesRepository;

import java.util.List;

/**
 * Service para lógica de negocio de notas.
 * Gestiona las operaciones CRUD de notas y su asociación con tareas.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class NotesService {

    private final INotesRepository repo;
    private final NoteValidator validator;

    /**
     * Constructor del servicio de notas.
     *
     * @param repo Repositorio de notas
     */
    public NotesService(INotesRepository repo) {
        this.repo = repo;
        this.validator = new NoteValidator();
    }

    /**
     * Crea una nueva nota sin tarea asociada.
     *
     * @param body Contenido de la nota
     * @return La nota creada con su ID generado
     * @throws ValidationException si el body es inválido
     */
    public Note createNote(String body) {
        validator.validateBody(body);
        CreateNoteRequest request = new CreateNoteRequest(body, null);
        Note newNote = new Note(0, request.body(), request.taskId());
        int generatedId = repo.save(newNote);
        return new Note(generatedId, request.body(), request.taskId());
    }

    /**
     * Crea una nueva nota asociada a una tarea.
     *
     * @param body   Contenido de la nota
     * @param taskFk ID de la tarea asociada
     * @return La nota creada con su ID generado
     * @throws ValidationException si el body o taskFk son inválidos
     */
    public Note createNote(String body, int taskFk) {
        validator.validateBody(body);
        validator.validateTaskFk(taskFk > 0 ? taskFk : null);
        CreateNoteRequest request = new CreateNoteRequest(body, taskFk > 0 ? taskFk : null);
        Note newNote = new Note(0, request.body(), request.taskId());
        int generatedId = repo.save(newNote);
        return new Note(generatedId, request.body(), request.taskId());
    }

    /**
     * Obtiene todas las notas de la agenda.
     *
     * @return Lista de todas las notas
     */
    public List<Note> getAllNotes() {
        return repo.findAll();
    }

    /**
     * Busca una nota por su identificador.
     *
     * @param id Identificador de la nota
     * @return La nota encontrada
     * @throws EntityNotFoundException si no se encuentra la nota
     */
    public Note findNoteById(int id) {
        return repo.findById(id);
    }

    /**
     * Elimina una nota de la agenda.
     *
     * @param id Identificador de la nota a eliminar
     */
    public void deleteNoteById(int id) {
        repo.delete(id);
    }

    /**
     * Actualiza una nota existente.
     *
     * @param note Nota con los nuevos datos
     * @throws ValidationException si el body o task_fk son inválidos
     */
    public void updateNote(Note note) {
        validator.validateBody(note.getBody());
        validator.validateTaskFk(note.getTask_fk());
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

    /**
     * Obtiene las notas asociadas a una tarea específica.
     *
     * @param taskId Identificador de la tarea
     * @return Lista de notas asociadas a la tarea
     */
    public List<Note> getNotesByTaskId(int taskId) {
        return repo.findByTaskId(taskId);
    }
}