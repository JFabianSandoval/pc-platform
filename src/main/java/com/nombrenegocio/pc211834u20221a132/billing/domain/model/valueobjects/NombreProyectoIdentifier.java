package com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects;

import java.util.UUID;

/**
 * Value object representing the NombreProyecto invoice identifier.
 *
 * <p>
 * This value object uniquely identifies an Invoice document. Its value is a
 * UUID that is generated externally, by the procurement bounded context, and
 * handed over to billing — it is never generated within this bounded context.
 * Because of that, this value object intentionally has no default constructor:
 * a valid identifier must always be supplied from the outside.
 * It throws an IllegalArgumentException if the identifier is null.
 * </p>
 *
 * @param identifier The externally generated UUID that identifies the invoice. It cannot be null.
 */
public record NombreProyectoIdentifier(UUID identifier) {

    /**
     * Compact constructor for NombreProyectoIdentifier.
     * Validates that the identifier is not null.
     * @throws IllegalArgumentException if the identifier is null.
     */
    public NombreProyectoIdentifier {
        if (identifier == null) {
            throw new IllegalArgumentException("NombreProyecto identifier cannot be null");
        }
    }
}