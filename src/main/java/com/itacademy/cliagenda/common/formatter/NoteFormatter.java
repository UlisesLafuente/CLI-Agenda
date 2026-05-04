package com.itacademy.cliagenda.common.formatter;

import com.itacademy.cliagenda.note.model.Note;

import java.util.List;

public class NoteFormatter {

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

    public String formatDetail(Note note) {
        if (note == null) {
            return "Note not found";
        }
        return "ID: " + note.getId() + "\n" +
                "Body: " + note.getBody() + "\n" +
                "Task FK: " + (note.getTask_fk() != null ? note.getTask_fk() : "None") + "\n";
    }
}