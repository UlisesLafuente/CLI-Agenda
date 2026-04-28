package com.itacademy.cliagenda.event.cli;

import com.itacademy.cliagenda.common.exception.EntityNotFoundException;
import com.itacademy.cliagenda.common.exception.ValidationException;
import com.itacademy.cliagenda.event.model.Event;
import com.itacademy.cliagenda.event.service.EventService;
import com.itacademy.cliagenda.task.service.TaskService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * CLI para operaciones de eventos.
 * Solo maneja input/output, la lógica de negocio está en EventService.
 *
 * @author CLI-Agenda
 * @version 1.0
 * @since 2026
 */
public class EventCli {

    private final EventService eventService;
    private final TaskService taskService;
    private final Scanner scanner = new Scanner(System.in);

    public EventCli(EventService eventService, TaskService taskService) {
        this.eventService = eventService;
        this.taskService = taskService;
    }

    public void showMenu() {
        int option = -1;
        do {
            System.out.println("<< EVENTS MENU >>");
            System.out.println("1 - Create event");
            System.out.println("2 - List events");
            System.out.println("3 - Find event");
            System.out.println("4 - Update event");
            System.out.println("5 - Delete event");
            System.out.println("0 - Return to App Menu");

            option = readInt();
            scanner.nextLine();

            try {
                switch (option) {
                    case 1:
                        createEvent();
                        break;
                    case 2:
                        listEvents();
                        break;
                    case 3:
                        findEvent();
                        break;
                    case 4:
                        updateEvent();
                        break;
                    case 5:
                        deleteEvent();
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

    private void createEvent() {
        System.out.println("Introduce Event title:");
        String title = scanner.nextLine();
        System.out.println("Introduce Event description:");
        String description = scanner.nextLine();

        LocalDateTime dateTime = null;
        boolean validDate = false;
        while (!validDate) {
            System.out.println("Introduce date (yyyy-MM-dd HH:mm):");
            String dateText = scanner.nextLine();
            try {
                dateTime = LocalDateTime.parse(dateText,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                validDate = true;
            } catch (Exception e) {
                System.out.println("Invalid date format. Use the format yyyy-MM-dd HH:mm");
            }
        }

        System.out.println("Recurring? (Y/N):");
        boolean recurring = scanner.nextLine().equalsIgnoreCase("y");

        boolean annualRecurring = false;
        int recurrenceInterval = 0;

        if (recurring) {
            System.out.println("Annual recurring? (Y/N):");
            annualRecurring = scanner.nextLine().equalsIgnoreCase("y");
            if (!annualRecurring) {
                System.out.println("Recurrence interval in months:");
                recurrenceInterval = readInt();
                scanner.nextLine();
            }
        }

        Event event = eventService.createEvent(title, description, dateTime, recurring, annualRecurring, recurrenceInterval);
        System.out.println("Event \"" + event.getTitle() + "\" created.");
    }

    private void listEvents() {
        System.out.println(eventService.formatEventList(eventService.getAllEvents()));
    }

    private void findEvent() {
        System.out.println("Introduce event ID:");
        int id = readInt();
        scanner.nextLine();

        Event event = eventService.findEventById(id);
        System.out.println(eventService.formatEventDetail(event));

        if (event != null) {
            var tasks = taskService.getTasksByEventId(id);
            if (!tasks.isEmpty()) {
                System.out.println("Associated tasks:");
                tasks.forEach(task ->
                        System.out.println("  ID: " + task.getId() + " - " + task.getBody()
                                + " (Completed: " + (task.isCompleted() ? "Yes" : "No") + ")"));
            } else {
                System.out.println("No associated tasks.");
            }
        }
    }

    private void deleteEvent() {
        System.out.println("Introduce event ID to delete:");
        int id = readInt();
        scanner.nextLine();

        eventService.deleteEventById(id);
        System.out.println("Event deleted.");
    }

    private void updateEvent() {
        System.out.println("Introduce event ID to update:");
        int id = readInt();
        scanner.nextLine();

        Event event = eventService.findEventById(id);
        System.out.println(eventService.formatEventDetail(event));
        System.out.println();

        System.out.println("Do you want to modify the title? (Y/N):");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            System.out.println("Introduce new title:");
            String newTitle = scanner.nextLine();
            event.changeTitle(newTitle);
        }

        System.out.println("Do you want to modify the description? (Y/N):");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            System.out.println("Introduce new description:");
            String newDesc = scanner.nextLine();
            event.changeDescription(newDesc);
        }

        System.out.println("Do you want to modify the date? (Y/N):");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            LocalDateTime newDateTime = null;
            boolean validDate = false;
            while (!validDate) {
                System.out.println("Introduce new date (yyyy-MM-dd HH:mm):");
                String dateText = scanner.nextLine();
                try {
                    newDateTime = LocalDateTime.parse(dateText,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    validDate = true;
                } catch (Exception e) {
                    System.out.println("Invalid date format. Use the format yyyy-MM-dd HH:mm");
                }
            }
            event.changeDateEvent(newDateTime);
        }

        System.out.println("Do you want to modify the recurring status? (Y/N):");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            System.out.println("Mark as recurring? (Y/N):");
            boolean recurring = scanner.nextLine().equalsIgnoreCase("y");
            event.setRecurring(recurring);

            if (recurring) {
                System.out.println("Annual recurring? (Y/N):");
                boolean annualRecurring = scanner.nextLine().equalsIgnoreCase("y");
                event.setAnnualRecurring(annualRecurring);
                if (!annualRecurring) {
                    System.out.println("Recurrence interval in months:");
                    int recurrenceInterval = readInt();
                    scanner.nextLine();
                    event.setRecurrenceInterval(recurrenceInterval);
                }
            }
        }

        eventService.updateEvent(event);
        System.out.println("Event updated successfully.");
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