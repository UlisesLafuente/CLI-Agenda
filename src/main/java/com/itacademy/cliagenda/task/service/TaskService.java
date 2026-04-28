package com.itacademy.cliagenda.task.service;

import com.itacademy.cliagenda.task.dto.CreateTaskRequest;
import com.itacademy.cliagenda.task.dto.UpdateTaskRequest;
import com.itacademy.cliagenda.common.exception.ValidationException;
import com.itacademy.cliagenda.event.service.EventService;
import com.itacademy.cliagenda.note.service.NotesService;
import com.itacademy.cliagenda.task.model.Task;
import com.itacademy.cliagenda.task.repository.TaskRepository;

import java.util.List;

/**
 * Service para lógica de negocio de tareas.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class TaskService {

    private final TaskRepository repo;
    private final NotesService notesService;
    private final EventService eventService;

    public TaskService(TaskRepository repo) {
        this(repo, null, null);
    }

    public TaskService(TaskRepository repo, NotesService notesService, EventService eventService) {
        this.repo = repo;
        this.notesService = notesService;
        this.eventService = eventService;
    }

    public Task createTask(String body) {
        CreateTaskRequest request = new CreateTaskRequest(body, null);
        int id = generateNextId();
        Task newTask = new Task(id, request.body(), request.eventId() != null ? request.eventId() : 0);
        repo.save(newTask);
        return newTask;
    }

    public Task createTask(String body, int eventFk) {
        CreateTaskRequest request = new CreateTaskRequest(body, eventFk > 0 ? eventFk : null);
        int id = generateNextId();
        Task newTask = new Task(id, request.body(), request.eventId() != null ? request.eventId() : 0);
        repo.save(newTask);
        return newTask;
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

    public String formatTaskList(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            return "No tasks found";
        }
        StringBuilder sb = new StringBuilder();
        for (Task task : tasks) {
            sb.append("ID: ").append(task.getId())
                    .append(" | ").append(task.getBody())
                    .append(" | Completed: ").append(task.isCompleted() ? "Yes" : "No")
                    .append("\n");
        }
        return sb.toString();
    }

    public String formatTaskDetail(Task task) {
        if (task == null) {
            return "Task not found";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(task.getId()).append("\n");
        sb.append("  Body: ").append(task.getBody()).append("\n");
        sb.append("  Completed: ").append(task.isCompleted() ? "Yes" : "No").append("\n");
        sb.append("  Associated to event: ").append(task.getEvent_fk()).append("\n");

        if (notesService != null && task.getId() > 0) {
            var notes = notesService.getNotesByTaskId(task.getId());
            if (!notes.isEmpty()) {
                sb.append("  Associated notes:\n");
                for (var note : notes) {
                    sb.append("    - ").append(note.getBody()).append("\n");
                }
            }
        }
        return sb.toString();
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

    private void validateTaskBody(String body) {
        if (body == null || body.trim().isEmpty()) {
            throw new ValidationException("Task body cannot be empty");
        }
    }

    int generateNextId() {
        List<Task> tasks = repo.findAll();
        int maxId = tasks.stream()
                .mapToInt(Task::getId)
                .max()
                .orElse(0);
        return maxId + 1;
    }
}