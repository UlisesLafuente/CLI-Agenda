package com.itacademy.cliagenda.event.repository;

import com.itacademy.cliagenda.event.model.Event;

import java.util.List;

/**
 * Interfaz para el repositorio de eventos.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public interface IEventRepository {

    List<Event> findAll();

    Event findById(int id);

    void save(Event event);

    void update(Event event);

    void delete(int id);
}