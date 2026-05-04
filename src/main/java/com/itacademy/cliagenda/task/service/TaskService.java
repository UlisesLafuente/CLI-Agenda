package com.itacademy.cliagenda.task.service;

import com.itacademy.cliagenda.common.validator.TaskValidator;
import com.itacademy.cliagenda.event.service.EventService;
import com.itacademy.cliagenda.note.model.Note;
import com.itacademy.cliagenda.note.service.NotesService;
import com.itacademy.cliagenda.task.dto.CreateTaskRequest;
import com.itacademy.cliagenda.task.dto.UpdateTaskRequest;
import com.itacademy.cliagenda.task.model.Task;
import com.itacademy.cliagenda.task.repository.ITaskRepository;

import java.util.List;

/**
 * Service para lógica de negocio de tareas.
 * Gestiona las operaciones CRUD de tareas, filtrado y asociación con eventos.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class TaskService {

    private final ITaskRepository repo;
    private final NotesService notesService;
    private final EventService eventService;
    private final TaskValidator validator;

    /**
     * Constructor básico del servicio de tareas.
     *
     * @param repo Repositorio de tareas
     */
    public TaskService(ITaskRepository repo) {
        this(repo, null, null);
    }

    /**
     * Constructor completo del servicio de tareas.
     *
     * @param repo           Repositorio de tareas
     * @param notesService   Servicio de notas (puede ser null)
     * @param eventService   Servicio de eventos (puede ser null)
     */
    public TaskService(ITaskRepository repo, NotesService notesService, EventService eventService) {
        this.repo = repo;
        this.notesService = notesService;
        this.eventService = eventService;
        this.validator = new TaskValidator();
    }

    /**
     * Crea una nueva tarea sin evento asociado.
     *
     * @param body Contenido de la tarea
     * @return La tarea creada con su ID generado
     * @throws ValidationException si el body es inválido
     */
    public Task createTask(String body) {
        validator.validateBody(body);
        CreateTaskRequest request = new CreateTaskRequest(body, null);
        Task newTask = new Task(0, request.body(), request.eventId());
        int generatedId = repo.save(newTask);
        return new Task(generatedId, request.body(), request.eventId());
    }

    /**
     * Crea una nueva tarea asociada a un evento.
     *
     * @param body    Contenido de la tarea
     * @param eventFk ID del evento asociado
     * @return La tarea creada con su ID generado
     * @throws ValidationException si el body o eventFk son inválidos
     */
    public Task createTask(String body, int eventFk) {
        validator.validateBody(body);
        validator.validateEventFk(eventFk > 0 ? eventFk : null);
        CreateTaskRequest request = new CreateTaskRequest(body, eventFk > 0 ? eventFk : null);
        Task newTask = new Task(0, request.body(), request.eventId());
        int generatedId = repo.save(newTask);
        return new Task(generatedId, request.body(), request.eventId());
    }

    /**
     * Obtiene todas las tareas de la agenda.
     *
     * @return Lista de todas las tareas
     */
    public List<Task> getAllTasks() {
        return repo.findAll();
    }

    /**
     * Busca una tarea por su identificador.
     *
     * @param id Identificador de la tarea
     * @return La tarea encontrada
     * @throws EntityNotFoundException si no se encuentra la tarea
     */
    public Task findTaskById(int id) {
        return repo.findById(id);
    }

    /**
     * Elimina una tarea de la agenda.
     *
     * @param id Identificador de la tarea a eliminar
     */
    public void deleteTaskById(int id) {
        repo.delete(id);
    }

    /**
     * Actualiza una tarea existente.
     *
     * @param task Tarea con los nuevos datos
     * @throws ValidationException si el body o event_fk son inválidos
     */
    public void updateTask(Task task) {
        validator.validateBody(task.getBody());
        validator.validateEventFk(task.getEvent_fk());
        UpdateTaskRequest request = new UpdateTaskRequest(task.getBody(), task.getEvent_fk(), task.isCompleted());
        Task existingTask = repo.findById(task.getId());

        if (request.body() != null && !request.body().isEmpty()) {
            existingTask.changeBody(request.body());
        }
        if (request.eventId() != null) {
            existingTask.setEvent_fk(request.eventId());
        }
        if (request.completed() != null) {
            existingTask.setCompleted(request.completed());
        }

        repo.update(existingTask);
    }

    /**
     * Obtiene las tareas filtradas por estado de completitud.
     *
     * @param completed true para tareas completadas, false para incompletas
     * @return Lista de tareas que coinciden con el filtro
     */
    public List<Task> getTasksByCompleted(boolean completed) {
        return repo.findByCompleted(completed);
    }

    /**
     * Marca una tarea como completada o incompleta.
     *
     * @param id        Identificador de la tarea
     * @param completed true para marcar como completada
     */
    public void markTaskCompleted(int id, boolean completed) {
        Task task = repo.findById(id);
        task.setCompleted(completed);
        repo.update(task);
    }

    /**
     * Obtiene las tareas asociadas a un evento específico.
     *
     * @param eventId Identificador del evento
     * @return Lista de tareas asociadas al evento
     */
    public List<Task> getTasksByEventId(int eventId) {
        return repo.findByEventId(eventId);
    }

    /**
     * Verifica si existe una tarea con el identificador dado.
     *
     * @param id Identificador a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean taskExists(int id) {
        try {
            repo.findById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtiene una lista formateada de eventos disponibles para asociar.
     *
     * @return String con los IDs y títulos de eventos disponibles
     */
    public String getAvailableEventIds() {
        if (eventService == null) {
            return "";
        }
        var events = eventService.getAllEvents();
        if (events.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (var event : events) {
            sb.append("  ID: ").append(event.getId()).append(" - ").append(event.getTitle()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Obtiene las notas asociadas a una tarea específica.
     *
     * @param taskId Identificador de la tarea
     * @return Lista de notas asociadas a la tarea
     */
    public List<Note> getNotesForTask(int taskId) {
        if (notesService != null) {
            return notesService.getNotesByTaskId(taskId);
        }
        return List.of();
    }
}