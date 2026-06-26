package com.nombrenegocio.pc211834u20221a132.billing.application.commands;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Create Invoice Command.
 *
 * <p>
 * Carries the raw input data required to create a new Invoice. Primitive
 * types are used here on purpose: this command represents the boundary
 * between the outside world (e.g. a REST request) and the domain, where
 * Value Objects are constructed and validated.
 * </p>
 */
public record CreateInvoiceCommand(UUID invoiceIdentifier,
                                   Long customerId,
                                   LocalDate billingStartDate,
                                   LocalDate billingEndDate,
                                   BigDecimal totalAmountValue,
                                   String totalAmountCurrency,
                                   String status,
                                   String memo) {
}