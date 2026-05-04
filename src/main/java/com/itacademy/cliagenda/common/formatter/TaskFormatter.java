package com.itacademy.cliagenda.common.formatter;

import com.itacademy.cliagenda.task.model.Task;

import java.util.List;

public class TaskFormatter {

    public String formatList(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            return "No tasks found";
        }
        StringBuilder sb = new StringBuilder();
        for (Task task : tasks) {
            sb.append("ID: ").append(task.getId())
                    .append(" | ").append(task.getBody())
                    .append(" | Completed: ").append(task.isCompleted() ? "Yes" : "No")
                    .append("\n");
        }
        return sb.toString();
    }

    public String formatDetail(Task task) {
        if (task == null) {
            return "Task not found";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(task.getId()).append("\n");
        sb.append("  Body: ").append(task.getBody()).append("\n");
        sb.append("  Completed: ").append(task.isCompleted() ? "Yes" : "No").append("\n");
        sb.append("  Associated to event: ").append(task.getEvent_fk() != null ? task.getEvent_fk() : "None").append("\n");
        return sb.toString();
    }
}