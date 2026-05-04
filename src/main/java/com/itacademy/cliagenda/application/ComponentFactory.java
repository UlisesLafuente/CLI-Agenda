package com.itacademy.cliagenda.application;

import com.itacademy.cliagenda.event.repository.EventRepository;
import com.itacademy.cliagenda.event.repository.IEventRepository;
import com.itacademy.cliagenda.event.service.EventService;
import com.itacademy.cliagenda.note.repository.INotesRepository;
import com.itacademy.cliagenda.note.repository.NotesRepository;
import com.itacademy.cliagenda.note.service.NotesService;
import com.itacademy.cliagenda.task.repository.ITaskRepository;
import com.itacademy.cliagenda.task.repository.TaskRepository;
import com.itacademy.cliagenda.task.service.TaskService;

/**
 * Fábrica centralizada para la creación de componentes de la aplicación.
 * Implementa un simple Service Locator para manejar dependencias.
 * <p>
 * Este enfoque permite:
 * <ul>
 *   <li>Centralizar la creación de componentes</li>
 *   <li>Gestionar el ciclo de vida de las dependencias</li>
 *   <li>Facilitar testing con mocks</li>
 * </ul>
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class ComponentFactory {

    private static ComponentFactory instance;

    private final IEventRepository eventRepository;
    private final ITaskRepository taskRepository;
    private final INotesRepository notesRepository;

    private final EventService eventService;
    private final NotesService notesService;
    private final TaskService taskService;

    private ComponentFactory() {
        this.eventRepository = new EventRepository();
        this.taskRepository = new TaskRepository();
        this.notesRepository = new NotesRepository();

        this.eventService = new EventService(eventRepository);
        this.notesService = new NotesService(notesRepository);
        this.taskService = new TaskService(taskRepository, notesService, eventService);
    }

    public static ComponentFactory getInstance() {
        if (instance == null) {
            instance = new ComponentFactory();
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    public IEventRepository getEventRepository() {
        return eventRepository;
    }

    public ITaskRepository getTaskRepository() {
        return taskRepository;
    }

    public INotesRepository getNotesRepository() {
        return notesRepository;
    }

    public EventService getEventService() {
        return eventService;
    }

    public NotesService getNotesService() {
        return notesService;
    }

    public TaskService getTaskService() {
        return taskService;
    }
}