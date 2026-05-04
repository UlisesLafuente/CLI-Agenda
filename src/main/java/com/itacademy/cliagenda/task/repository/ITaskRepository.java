package com.itacademy.cliagenda.task.repository;

import com.itacademy.cliagenda.task.model.Task;

import java.util.List;

/**
 * Interfaz para el repositorio de tareas.
 * Define el contrato para las operaciones de acceso a datos de tareas.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public interface ITaskRepository {

    /**
     * Obtiene todas las tareas de la base de datos.
     *
     * @return Lista de todas las tareas
     */
    List<Task> findAll();

    /**
     * Busca una tarea por su identificador.
     *
     * @param id Identificador de la tarea
     * @return La tarea encontrada
     * @throws EntityNotFoundException si no se encuentra la tarea
     */
    Task findById(int id);

    /**
     * Guarda una nueva tarea en la base de datos.
     *
     * @param task Tarea a guardar
     * @return ID generado para la tarea
     */
    int save(Task task);

    /**
     * Actualiza una tarea existente en la base de datos.
     *
     * @param task Tarea con los datos actualizados
     */
    void update(Task task);

    /**
     * Elimina una tarea de la base de datos.
     *
     * @param id Identificador de la tarea a eliminar
     */
    void delete(int id);

    /**
     * Busca las tareas asociadas a un evento específico.
     *
     * @param eventId Identificador del evento
     * @return Lista de tareas asociadas al evento
     */
    List<Task> findByEventId(int eventId);

    /**
     * Busca las tareas filtradas por estado de completitud.
     *
     * @param completed true para tareas completadas, false para incompletas
     * @return Lista de tareas que coinciden con el filtro
     */
    List<Task> findByCompleted(boolean completed);
}