package com.itacademy.cliagenda.event.model;

import java.time.LocalDateTime;

public class Event {

    private final int Id;
    private String title;
    private String description;
    private LocalDateTime dateTimeEvent;
    private boolean recurring;
    private boolean annualRecurring;
    private int recurrenceInterval;

    public Event(int Id, String title, String description, LocalDateTime dateTimeEvent, boolean recurring, boolean annualRecurring, int recurrenceInterval) {
        this.Id = Id;
        this.title = title;
        this.description = description;
        this.dateTimeEvent = dateTimeEvent;
        this.recurring = recurring;
        this.annualRecurring = annualRecurring;
        this.recurrenceInterval = recurrenceInterval;
    }

    public int getId() {
        return Id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDateTimeEvent() {
        return dateTimeEvent;
    }

    public int getRecurrenceInterval() {
        return recurrenceInterval;
    }

    public void setRecurrenceInterval(int recurrenceInterval) {
        this.recurrenceInterval = recurrenceInterval;
    }

    public boolean isAnnualRecurring() {
        return annualRecurring;
    }

    public void setAnnualRecurring(boolean annualRecurring) {
        this.annualRecurring = annualRecurring;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public void changeTitle(String title) {
        try {
            if (title == null) throw new IllegalArgumentException("Title can't be null");
            if (title.length() >= 100) throw new IllegalArgumentException("Title must be shorter than 100 characters");
            this.title = title;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void changeDescription(String description) {
        try {
            if (description != null && description.length() >= 500)
                throw new IllegalArgumentException("Description must be shorter than 500 characters");
            this.description = description;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void changeDateEvent(LocalDateTime datetime) {
        this.dateTimeEvent = datetime;
    }
}
