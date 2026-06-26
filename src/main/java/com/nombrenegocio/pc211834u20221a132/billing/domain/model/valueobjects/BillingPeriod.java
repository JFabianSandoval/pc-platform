package com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects;

import java.time.LocalDate;

/**
 * Value object representing the billing period covered by an invoice.
 *
 * <p>
 * This value object encapsulates the start and end dates of the period that
 * an invoice covers. It is immutable.
 * It throws an IllegalArgumentException if either date is null, or if the
 * end date is before the start date.
 * </p>
 *
 * @param startDate The first day of the billing period. It cannot be null.
 * @param endDate The last day of the billing period. It cannot be null and must not be before startDate.
 */
public record BillingPeriod(LocalDate startDate, LocalDate endDate) {

    /**
     * Compact constructor for BillingPeriod.
     * Validates that both dates are present and that the period is chronologically valid.
     * @throws IllegalArgumentException if startDate or endDate is null, or endDate is before startDate.
     */
    public BillingPeriod {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Billing period start date and end date cannot be null");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Billing period end date cannot be before start date");
        }
    }
}
