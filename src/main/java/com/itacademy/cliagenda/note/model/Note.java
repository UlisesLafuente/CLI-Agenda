package com.itacademy.cliagenda.note.model;

import com.itacademy.cliagenda.common.exception.ValidationException;

public class Note {
    private final int id;
    private String body;
    private Integer task_fk;

    public Note(int id, String body, Integer task_fk) {
        this.id = id;
        this.body = body;
        this.task_fk = task_fk;
    }

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void changeBody(String body) {
        if (body != null && body.length() > 250) {
            throw new ValidationException("Note body exceeds maximum length of 250 characters");
        }
        this.body = body;
    }

    public Integer getTask_fk() {
        return task_fk;
    }

    public void setTask_fk(Integer task_fk) {
        if (task_fk != null && task_fk < 0) {
            throw new ValidationException("Task FK cannot be negative");
        }
        this.task_fk = task_fk;
    }
}
