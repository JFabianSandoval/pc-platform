package com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects;

/**
 * Enumeration representing the possible statuses of an invoice.
 *
 * <p>
 * NOTE: adjust these values to match the exact statuses defined by your
 * Chargebee integration / course requirements. These are placeholders based
 * on the typical Chargebee invoice lifecycle.
 * </p>
 */
public enum InvoiceStatus {
    PAID,
    PENDING,
    NOT_PAID,
    VOIDED
}
