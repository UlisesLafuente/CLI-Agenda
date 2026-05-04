package com.itacademy.cliagenda.common.formatter;

import com.itacademy.cliagenda.task.model.Task;

import java.util.List;

/**
 * Formateador para entidades de tipo Task.
 * Proporciona métodos para convertir tareas en representaciones de texto legibles
 * para mostrar en la consola.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class TaskFormatter {

    /**
     * Formatea una lista de tareas en una representación de texto legible.
     * Cada tarea se muestra en una línea con su ID, contenido y estado de completitud.
     *
     * @param tasks Lista de tareas a formatear
     * @return String con la representación formateada de la lista de tareas
     */
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

    /**
     * Formatea los detalles de una tarea individual.
     * Muestra el ID, contenido, estado de completitud y evento asociado.
     *
     * @param task Tarea a formatear
     * @return String con los detalles formateados de la tarea
     */
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