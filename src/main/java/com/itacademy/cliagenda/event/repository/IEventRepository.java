package com.itacademy.cliagenda.event.repository;

import com.itacademy.cliagenda.event.model.Event;

import java.util.List;

/**
 * Interfaz para el repositorio de eventos.
 * Define el contrato para las operaciones de acceso a datos de eventos.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public interface IEventRepository {

    /**
     * Obtiene todos los eventos de la base de datos.
     *
     * @return Lista de todos los eventos
     */
    List<Event> findAll();

    /**
     * Busca un evento por su identificador.
     *
     * @param id Identificador del evento
     * @return El evento encontrado
     * @throws EntityNotFoundException si no se encuentra el evento
     */
    Event findById(int id);

    /**
     * Guarda un nuevo evento en la base de datos.
     *
     * @param event Evento a guardar
     * @return ID generado para el evento
     */
    int save(Event event);

    /**
     * Actualiza un evento existente en la base de datos.
     *
     * @param event Evento con los datos actualizados
     */
    void update(Event event);

    /**
     * Elimina un evento de la base de datos.
     *
     * @param id Identificador del evento a eliminar
     */
    void delete(int id);
}