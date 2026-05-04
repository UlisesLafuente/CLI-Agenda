package com.itacademy.cliagenda.note.repository;

import com.itacademy.cliagenda.note.model.Note;

import java.util.List;

/**
 * Interfaz para el repositorio de notas.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public interface INotesRepository {

    List<Note> findAll();

    Note findById(int id);

    int save(Note note);

    void update(Note note);

    void delete(int id);

    List<Note> findByTaskId(int taskId);
}