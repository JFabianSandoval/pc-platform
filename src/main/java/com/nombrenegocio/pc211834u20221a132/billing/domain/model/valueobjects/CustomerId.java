package com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects;

/**
 * Value object representing the identifier of the customer associated with an invoice.
 *
 * <p>
 * This value object wraps the technical identifier of a Customer, so that an
 * Invoice references a Customer through a typed value object instead of a raw Long.
 * It throws an IllegalArgumentException if the value is null or not a positive number.
 * </p>
 *
 * @param value The customer identifier. It must be a positive number.
 */
public record CustomerId(Long value) {

    /**
     * Compact constructor for CustomerId.
     * Validates that the value is not null and is greater than zero.
     * @throws IllegalArgumentException if the value is null or not greater than zero.
     */
    public CustomerId {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Customer id must be a positive number");
        }
    }
}
