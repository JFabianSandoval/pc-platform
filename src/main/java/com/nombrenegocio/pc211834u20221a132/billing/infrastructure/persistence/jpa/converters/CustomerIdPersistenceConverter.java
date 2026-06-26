package com.nombrenegocio.pc211834u20221a132.billing.infrastructure.persistence.jpa.converters;

//el atributo CustomerId necesita converters xq Un solo campo (UUID) → necesita AttributeConverter

import com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects.CustomerId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts CustomerId value objects between the domain model and persistence column values.
 */
@Converter(autoApply = false)
public class CustomerIdPersistenceConverter implements AttributeConverter<CustomerId, Long> {

    @Override
    public Long convertToDatabaseColumn(CustomerId attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public CustomerId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new CustomerId(dbData);
    }
}
