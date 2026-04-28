package com.itacademy.cliagenda.note.cli;

import com.itacademy.cliagenda.common.exception.EntityNotFoundException;
import com.itacademy.cliagenda.common.exception.ValidationException;
import com.itacademy.cliagenda.note.model.Note;
import com.itacademy.cliagenda.note.service.NotesService;
import com.itacademy.cliagenda.task.service.TaskService;

import java.util.Scanner;

/**
 * CLI para operaciones de notas.
 * Solo maneja input/output, la lógica de negocio está en NotesService.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class NoteCli {

    private final NotesService notesService;
    private final TaskService taskService;
    private final Scanner scanner = new Scanner(System.in);

    public NoteCli(NotesService notesService, TaskService taskService) {
        this.notesService = notesService;
        this.taskService = taskService;
    }

    public void showMenu() {
        int option = -1;
        do {
            System.out.println("<< NOTES MENU >>");
            System.out.println("1 - Create note");
            System.out.println("2 - List notes");
            System.out.println("3 - Find note");
            System.out.println("4 - Update note");
            System.out.println("5 - Delete note");
            System.out.println("0 - Return to App Menu");

            option = readInt();
            scanner.nextLine();

            try {
                switch (option) {
                    case 1:
                        createNote();
                        break;
                    case 2:
                        listNotes();
                        break;
                    case 3:
                        findNote();
                        break;
                    case 4:
                        updateNote();
                        break;
                    case 5:
                        deleteNote();
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

    private void createNote() {
        var tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks available. You need to create a task first before adding a note.");
            return;
        }

        System.out.println("Available tasks:");
        tasks.forEach(task -> System.out.println("  ID: " + task.getId() + " - " + task.getBody()));

        System.out.println("Introduce note body:");
        String body = scanner.nextLine();

        System.out.println("Introduce 'task ID' to link this note to:");
        int idTaskForThisNote = readInt();
        scanner.nextLine();

        Note note = notesService.createNote(body, idTaskForThisNote);
        System.out.println("Note created with ID: " + note.getId() + " linked to task with ID #" + idTaskForThisNote);
    }

    private void listNotes() {
        System.out.println(notesService.formatNoteList(notesService.getAllNotes()));
    }

    private void findNote() {
        System.out.println("Introduce note ID to search it:");
        int id = readInt();
        scanner.nextLine();

        Note note = notesService.findNoteById(id);
        System.out.println(notesService.formatNoteDetail(note));
    }

    private void deleteNote() {
        System.out.println("Introduce note ID to delete it:");
        int id = readInt();
        scanner.nextLine();

        notesService.deleteNoteById(id);
        System.out.println("Note with id " + id + " is correctly deleted");
    }

    private void updateNote() {
        System.out.println("Introduce note ID to update:");
        int id = readInt();
        scanner.nextLine();

        Note note = notesService.findNoteById(id);
        System.out.println(notesService.formatNoteDetail(note));
        System.out.println();

        System.out.println("Do you want to modify the body? (Y/N):");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            System.out.println("Introduce new body:");
            String newBody = scanner.nextLine();
            note.changeBody(newBody);
        }

        var tasks = taskService.getAllTasks();
        if (!tasks.isEmpty()) {
            System.out.println("Do you want to modify the task association? (Y/N):");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                System.out.println("Available tasks:");
                tasks.forEach(task -> System.out.println("  ID: " + task.getId() + " - " + task.getBody()));
                System.out.println("Introduce new task ID:");
                int newTaskId = readInt();
                scanner.nextLine();
                note.setTask_fk(newTaskId);
            }
        }

        notesService.updateNote(note);
        System.out.println("Note updated successfully.");
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