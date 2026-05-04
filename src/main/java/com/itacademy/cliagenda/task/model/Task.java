package com.itacademy.cliagenda.task.model;

import com.itacademy.cliagenda.common.exception.ValidationException;

public class Task {
    private final int id;
    private String body;
    private Integer event_fk;
    private boolean completed;

    public Task(int id, String body) {
        this.id = id;
        this.body = body;
        this.event_fk = null;
        this.completed = false;
    }

    public Task(int id, String body, Integer event_fk) {
        this.id = id;
        this.body = body;
        this.event_fk = event_fk;
        this.completed = false;
    }

    public Task(int id, String body, Integer event_fk, boolean completed) {
        this.id = id;
        this.body = body;
        this.event_fk = event_fk;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void changeBody(String body) {
        if (body != null && body.length() > 250) {
            throw new ValidationException("Task body exceeds maximum length of 250 characters");
        }
        this.body = body;
    }

    public Integer getEvent_fk() {
        return event_fk;
    }

    public void setEvent_fk(Integer event_fk) {
        if (event_fk != null && event_fk < 0) {
            throw new ValidationException("Event FK cannot be negative");
        }
        this.event_fk = event_fk;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
