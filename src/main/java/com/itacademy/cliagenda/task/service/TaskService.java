package com.itacademy.cliagenda.task.service;

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
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class TaskService {

    private final ITaskRepository repo;
    private final NotesService notesService;
    private final EventService eventService;

    public TaskService(ITaskRepository repo) {
        this(repo, null, null);
    }

    public TaskService(ITaskRepository repo, NotesService notesService, EventService eventService) {
        this.repo = repo;
        this.notesService = notesService;
        this.eventService = eventService;
    }

    public Task createTask(String body) {
        CreateTaskRequest request = new CreateTaskRequest(body, null);
        Task newTask = new Task(0, request.body(), request.eventId());
        int generatedId = repo.save(newTask);
        return new Task(generatedId, request.body(), request.eventId());
    }

    public Task createTask(String body, int eventFk) {
        CreateTaskRequest request = new CreateTaskRequest(body, eventFk > 0 ? eventFk : null);
        Task newTask = new Task(0, request.body(), request.eventId());
        int generatedId = repo.save(newTask);
        return new Task(generatedId, request.body(), request.eventId());
    }

    public List<Task> getAllTasks() {
        return repo.findAll();
    }

    public Task findTaskById(int id) {
        return repo.findById(id);
    }

    public void deleteTaskById(int id) {
        repo.delete(id);
    }

    public void updateTask(Task task) {
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

    public List<Task> getTasksByCompleted(boolean completed) {
        return repo.findByCompleted(completed);
    }

    public void markTaskCompleted(int id, boolean completed) {
        Task task = repo.findById(id);
        task.setCompleted(completed);
        repo.update(task);
    }

    public List<Task> getTasksByEventId(int eventId) {
        return repo.findByEventId(eventId);
    }

    public boolean taskExists(int id) {
        try {
            repo.findById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

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

    public List<Note> getNotesForTask(int taskId) {
        if (notesService != null) {
            return notesService.getNotesByTaskId(taskId);
        }
        return List.of();
    }
}