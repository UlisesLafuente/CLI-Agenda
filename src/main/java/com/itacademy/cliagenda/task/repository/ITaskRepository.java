package com.itacademy.cliagenda.task.repository;

import com.itacademy.cliagenda.task.model.Task;

import java.util.List;

/**
 * Interfaz para el repositorio de tareas.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public interface ITaskRepository {

    List<Task> findAll();

    Task findById(int id);

    int save(Task task);

    void update(Task task);

    void delete(int id);

    List<Task> findByEventId(int eventId);

    List<Task> findByCompleted(boolean completed);
}