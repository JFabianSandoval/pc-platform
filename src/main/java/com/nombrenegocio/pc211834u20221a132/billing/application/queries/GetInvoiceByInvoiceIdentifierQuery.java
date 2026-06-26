package com.nombrenegocio.pc211834u20221a132.billing.application.queries;

import java.util.UUID;

/**
 * Get Invoice By Invoice Identifier Query.
 */
public record GetInvoiceByInvoiceIdentifierQuery(UUID invoiceIdentifier) {
}
