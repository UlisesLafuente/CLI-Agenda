package com.itacademy.cliagenda.note.repository;

import com.itacademy.cliagenda.note.model.Note;

import java.util.List;

/**
 * Interfaz para el repositorio de notas.
 * Define el contrato para las operaciones de acceso a datos de notas.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public interface INotesRepository {

    /**
     * Obtiene todas las notas de la base de datos.
     *
     * @return Lista de todas las notas
     */
    List<Note> findAll();

    /**
     * Busca una nota por su identificador.
     *
     * @param id Identificador de la nota
     * @return La nota encontrada
     * @throws EntityNotFoundException si no se encuentra la nota
     */
    Note findById(int id);

    /**
     * Guarda una nueva nota en la base de datos.
     *
     * @param note Nota a guardar
     * @return ID generado para la nota
     */
    int save(Note note);

    /**
     * Actualiza una nota existente en la base de datos.
     *
     * @param note Nota con los datos actualizados
     */
    void update(Note note);

    /**
     * Elimina una nota de la base de datos.
     *
     * @param id Identificador de la nota a eliminar
     */
    void delete(int id);

    /**
     * Busca las notas asociadas a una tarea específica.
     *
     * @param taskId Identificador de la tarea
     * @return Lista de notas asociadas a la tarea
     */
    List<Note> findByTaskId(int taskId);
}