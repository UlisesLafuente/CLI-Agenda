package com.itacademy.cliagenda.common.formatter;

import com.itacademy.cliagenda.note.model.Note;

import java.util.List;

/**
 * Formateador para entidades de tipo Note.
 * Proporciona métodos para convertir notas en representaciones de texto legibles
 * para mostrar en la consola.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class NoteFormatter {

    /**
     * Formatea una lista de notas en una representación de texto legible.
     * Cada nota se muestra en una línea con su ID y contenido.
     *
     * @param notes Lista de notas a formatear
     * @return String con la representación formateada de la lista de notas
     */
    public String formatList(List<Note> notes) {
        if (notes == null || notes.isEmpty()) {
            return "No notes found";
        }
        StringBuilder sb = new StringBuilder();
        for (Note note : notes) {
            sb.append("ID: ").append(note.getId())
                    .append(" | ").append(note.getBody())
                    .append("\n");
        }
        return sb.toString();
    }

    /**
     * Formatea los detalles de una nota individual.
     * Muestra el ID, contenido y tarea asociada.
     *
     * @param note Nota a formatear
     * @return String con los detalles formateados de la nota
     */
    public String formatDetail(Note note) {
        if (note == null) {
            return "Note not found";
        }
        return "ID: " + note.getId() + "\n" +
                "Body: " + note.getBody() + "\n" +
                "Task FK: " + (note.getTask_fk() != null ? note.getTask_fk() : "None") + "\n";
    }
}