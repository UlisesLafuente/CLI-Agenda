package com.itacademy.cliagenda.application.menu;

import com.itacademy.cliagenda.application.ComponentFactory;
import com.itacademy.cliagenda.event.cli.EventCli;
import com.itacademy.cliagenda.note.cli.NoteCli;
import com.itacademy.cliagenda.task.cli.TaskCli;

import java.util.Scanner;

public class AppMenu {

    private final Scanner scanner = new Scanner(System.in);
    private int userOption = -1;

    private final TaskCli taskCli;
    private final NoteCli noteCli;
    private final EventCli eventCli;

    public AppMenu() {
        ComponentFactory factory = ComponentFactory.getInstance();
        this.taskCli = new TaskCli(factory.getTaskService());
        this.noteCli = new NoteCli(factory.getNotesService(), factory.getTaskService());
        this.eventCli = new EventCli(factory.getEventService(), factory.getTaskService());
    }

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