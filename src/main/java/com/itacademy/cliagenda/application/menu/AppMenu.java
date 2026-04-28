package com.itacademy.cliagenda.application.menu;

import com.itacademy.cliagenda.event.cli.EventCli;
import com.itacademy.cliagenda.event.repository.EventRepository;
import com.itacademy.cliagenda.event.service.EventService;
import com.itacademy.cliagenda.note.cli.NoteCli;
import com.itacademy.cliagenda.note.repository.NotesRepository;
import com.itacademy.cliagenda.note.service.NotesService;
import com.itacademy.cliagenda.task.cli.TaskCli;
import com.itacademy.cliagenda.task.repository.TaskRepository;
import com.itacademy.cliagenda.task.service.TaskService;

import java.util.Scanner;

public class AppMenu {

    Scanner scanner = new Scanner(System.in);
    int userOption = -1;

    EventRepository eventRepo = new EventRepository();
    EventService eventService = new EventService(eventRepo);

    TaskRepository taskRepo = new TaskRepository();
    NotesRepository notesRepo = new NotesRepository();
    NotesService notesService = new NotesService(notesRepo);

    TaskService taskService = new TaskService(taskRepo, notesService, eventService);

    TaskCli taskCli = new TaskCli(taskService);
    NoteCli noteCli = new NoteCli(notesService, taskService);
    EventCli eventCli = new EventCli(eventService, taskService);

    public void playMenu() {
        do {
            System.out.println("TASK / NOTES / EVENTS APP");
            System.out.println("1 - TASKS");
            System.out.println("2 - NOTES");
            System.out.println("3 - EVENTS");
            System.out.println("0 - Exit App");
            System.out.println("Select an option:");

            userOption = scanner.nextInt();
            scanner.nextLine();

            switch (userOption) {
                case (0):
                    break;
                case (1):
                    taskCli.showMenu();
                    break;
                case (2):
                    noteCli.showMenu();
                    break;
                case (3):
                    eventCli.showMenu();
                    break;
                default:
                    System.out.println("Incorrect input, try again.");
                    break;
            }
        } while (userOption != 0);
        System.out.println("Bye my friend...");
    }
}