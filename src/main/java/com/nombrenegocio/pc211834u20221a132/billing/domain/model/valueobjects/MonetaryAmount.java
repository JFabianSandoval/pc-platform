package com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects;

import java.math.BigDecimal;

/**
 * Value object representing a monetary amount with its currency.
 *
 * <p>
 * This value object encapsulates the total amount of an invoice together
 * with the currency it is expressed in, so both travel together instead of
 * being two unrelated primitive fields.
 * It throws an IllegalArgumentException if the amount is null or negative,
 * or if the currency is null or blank.
 * </p>
 *
 * @param amount The monetary amount. It cannot be null or negative.
 * @param currency The ISO currency code (e.g. "PEN", "USD"). It cannot be null or blank.
 */
public record MonetaryAmount(BigDecimal amount, String currency) {

    /**
     * Compact constructor for MonetaryAmount.
     * Validates that the amount is present and non-negative, and that the currency is present.
     * @throws IllegalArgumentException if amount is null/negative or currency is null/blank.
     */
    public MonetaryAmount {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Monetary amount cannot be null or negative");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Currency cannot be null or empty");
        }
    }
}