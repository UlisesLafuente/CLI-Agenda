package com.itacademy.cliagenda.task.repository;

import com.itacademy.cliagenda.event.model.Event;
import com.itacademy.cliagenda.event.repository.EventRepository;
import com.itacademy.cliagenda.task.model.Task;
import com.itacademy.cliagenda.testing.DatabaseTestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskRepositoryIntegrationTest {

    private TaskRepository taskRepository;
    private EventRepository eventRepository;

    @BeforeEach
    void setUp() throws Exception {
        taskRepository = new TaskRepository();
        eventRepository = new EventRepository();
        DatabaseTestContainer.clearTables();

        Event event1 = new Event(1, "Event 1", "Desc", LocalDateTime.now(), false, false, 0);
        Event event2 = new Event(2, "Event 2", "Desc", LocalDateTime.now(), false, false, 0);
        eventRepository.save(event1);
        eventRepository.save(event2);
    }

    @Test
    void shouldInsertTask() {
        Task task = new Task(1, "Buy groceries", 0);
        taskRepository.save(task);

        List<Task> tasks = taskRepository.findAll();
        assertEquals(1, tasks.size());
        assertEquals("Buy groceries", tasks.get(0).getBody());
    }

    @Test
    void shouldFindTaskById() {
        Task task = new Task(1, "Buy groceries", 0);
        taskRepository.save(task);

        Task found = taskRepository.findById(1);
        assertNotNull(found);
        assertEquals(1, found.getId());
    }

    @Test
    void shouldUpdateTask() {
        Task task = new Task(1, "Buy groceries", 1);
        taskRepository.save(task);

        task.setCompleted(true);
        taskRepository.update(task);

        Task found = taskRepository.findById(1);
        assertTrue(found.isCompleted());
    }

    @Test
    void shouldDeleteTask() {
        Task task = new Task(1, "Buy groceries", 0);
        taskRepository.save(task);

        taskRepository.delete(1);

        List<Task> tasks = taskRepository.findAll();
        assertTrue(tasks.isEmpty());
    }

    @Test
    void shouldFindTasksByEventId() {
        Task task1 = new Task(1, "Task 1", 1);
        Task task2 = new Task(2, "Task 2", 2);
        Task task3 = new Task(3, "Task 3", 1);
        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);

        List<Task> tasksForEvent1 = taskRepository.findByEventId(1);
        assertEquals(2, tasksForEvent1.size());
    }

    @Test
    void shouldFindTasksByCompleted() {
        Task task1 = new Task(1, "Task 1", 0, true);
        Task task2 = new Task(2, "Task 2", 0, false);
        taskRepository.save(task1);
        taskRepository.save(task2);

        List<Task> completedTasks = taskRepository.findByCompleted(true);
        assertEquals(1, completedTasks.size());
        assertTrue(completedTasks.get(0).isCompleted());
    }
}