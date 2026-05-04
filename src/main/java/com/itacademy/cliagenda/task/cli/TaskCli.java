package com.itacademy.cliagenda.task.cli;

import com.itacademy.cliagenda.common.exception.EntityNotFoundException;
import com.itacademy.cliagenda.common.exception.ValidationException;
import com.itacademy.cliagenda.common.formatter.TaskFormatter;
import com.itacademy.cliagenda.note.model.Note;
import com.itacademy.cliagenda.task.model.Task;
import com.itacademy.cliagenda.task.service.TaskService;

import java.util.List;
import java.util.Scanner;

/**
 * CLI para operaciones de tareas.
 * Solo maneja input/output, la lógica de negocio está en TaskService.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class TaskCli {

    private final TaskService service;
    private final TaskFormatter formatter;
    private final Scanner scanner = new Scanner(System.in);

    public TaskCli(TaskService service) {
        this.service = service;
        this.formatter = new TaskFormatter();
    }

    public void showMenu() {
        int option = -1;
        do {
            System.out.println("<< TASKS MENU >>");
            System.out.println("1 - Create task");
            System.out.println("2 - List all tasks");
            System.out.println("3 - List incomplete tasks");
            System.out.println("4 - List completed tasks");
            System.out.println("5 - Find task");
            System.out.println("6 - Update task");
            System.out.println("7 - Delete task");
            System.out.println("0 - Return to App Menu");

            option = readInt();
            scanner.nextLine();

            try {
                switch (option) {
                    case 1:
                        createTask();
                        break;
                    case 2:
                        listTasks();
                        break;
                    case 3:
                        listIncompleteTasks();
                        break;
                    case 4:
                        listCompletedTasks();
                        break;
                    case 5:
                        findTask();
                        break;
                    case 6:
                        updateTask();
                        break;
                    case 7:
                        deleteTask();
                        break;
                    default:
                        System.out.println("Incorrect input, try again.");
                        break;
                }
            } catch (ValidationException e) {
                System.out.println("Validation error: " + e.getMessage());
            } catch (EntityNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        } while (option != 0);
    }

    private void createTask() {
        System.out.println("Introduce task");
        String body = scanner.nextLine();

        Task task = service.createTask(body);
        System.out.println("Task \"" + task.getBody() + "\" created.");
    }

    private void listTasks() {
        System.out.println(formatter.formatList(service.getAllTasks()));
    }

    private void listIncompleteTasks() {
        System.out.println(formatter.formatList(service.getTasksByCompleted(false)));
    }

    private void listCompletedTasks() {
        System.out.println(formatter.formatList(service.getTasksByCompleted(true)));
    }

    private void findTask() {
        System.out.println("Available task IDs:");
        System.out.println(formatter.formatList(service.getAllTasks()));

        System.out.println("Introduce task ID:");
        int id = readInt();
        scanner.nextLine();

        Task task = service.findTaskById(id);
        String detail = formatter.formatDetail(task);
        System.out.println(detail);

        List<Note> notes = service.getNotesForTask(task.getId());
        if (!notes.isEmpty()) {
            System.out.println("  Associated notes:");
            for (Note note : notes) {
                System.out.println("    - " + note.getBody());
            }
        }
    }

    private void updateTask() {
        System.out.println("Introduce task ID to update:");
        int id = readInt();
        scanner.nextLine();

        Task task = service.findTaskById(id);
        System.out.println(formatter.formatDetail(task));
        System.out.println();

        System.out.println("Do you want to modify the body? (Y/N):");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            System.out.println("Introduce new body:");
            String newBody = scanner.nextLine();
            task.changeBody(newBody);
        }

        System.out.println("Do you want to mark as completed/incomplete? (Y/N):");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            System.out.println("Mark as completed? (Y/N):");
            String completed = scanner.nextLine();
            task.setCompleted(completed.equalsIgnoreCase("y"));
        }

        System.out.println(service.getAvailableEventIds());
        if (!service.getAvailableEventIds().isEmpty()) {
            System.out.println("Do you want to modify the event association? (Y/N):");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                System.out.println("Introduce new event ID (0 for none):");
                int newEventId = readInt();
                scanner.nextLine();
                task.setEvent_fk(newEventId == 0 ? null : newEventId);
            }
        }

        service.updateTask(task);
        System.out.println("Task updated successfully.");
    }

    private void deleteTask() {
        System.out.println("Introduce task ID to delete:");
        int id = readInt();
        scanner.nextLine();

        service.deleteTaskById(id);
        System.out.println("Task deleted.");
    }

    private int readInt() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }
}