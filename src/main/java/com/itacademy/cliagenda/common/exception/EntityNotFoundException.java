package com.itacademy.cliagenda.common.exception;

/**
 * Excepción para entidades no encontradas.
 *
 * @author Ulises Lafuente
 * @version 1.0
 * @since 2026
 */
public class EntityNotFoundException extends RuntimeException {

    private final String entityName;
    private final Object entityId;

    public EntityNotFoundException(String entityName, Object entityId) {
        super(entityName + " with id " + entityId + " not found");
        this.entityName = entityName;
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public Object getEntityId() {
        return entityId;
    }
}