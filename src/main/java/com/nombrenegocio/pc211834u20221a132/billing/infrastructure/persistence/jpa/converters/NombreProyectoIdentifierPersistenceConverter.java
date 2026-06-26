package com.nombrenegocio.pc211834u20221a132.billing.infrastructure.persistence.jpa.converters;

//el atributo NombreProyecto necesita converters xq Un solo campo (UUID) → necesita AttributeConverter

import com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects.NombreProyectoIdentifier;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.UUID;

/**
 * Converts NombreProyectoIdentifier value objects between the domain model and persistence column values.
 *
 * <p>
 * The UUID is stored as its String representation in the database column.
 * </p>
 */
@Converter(autoApply = false)
public class NombreProyectoIdentifierPersistenceConverter implements AttributeConverter<NombreProyectoIdentifier, String> {

    @Override
    public String convertToDatabaseColumn(NombreProyectoIdentifier attribute) {
        return attribute == null ? null : attribute.identifier().toString();
    }

    @Override
    public NombreProyectoIdentifier convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new NombreProyectoIdentifier(UUID.fromString(dbData));
    }
}