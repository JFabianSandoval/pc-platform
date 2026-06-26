package com.nombrenegocio.pc211834u20221a132.billing.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * Resource for an invoice.
 *
 * <p>
 * This is the flat, API-facing representation of an Invoice. Value Objects
 * used internally by the domain (NombreProyectoIdentifier, CustomerId,
 * BillingPeriod, MonetaryAmount) are flattened here into plain fields, as
 * required by the response contract.
 * </p>
 */
@Schema(
        name = "InvoiceResponse",
        description = "Invoice information response",
        example = "{\"id\": 1, \"invoiceId\": \"3f29c6b2-8b1a-4c2d-9a3e-7d9f6a2b1c44\", \"customerId\": \"1\", " +
                "\"billingStartDate\": \"2026-06-01\", \"billingEndDate\": \"2026-06-30\", " +
                "\"monetaryAmountValue\": 199.90, \"monetaryAmountCurrency\": \"PEN\", \"status\": \"PENDING\", \"memo\": \"Monthly subscription\"}"
)
public record InvoiceResource(
        @Schema(description = "Technical identifier of the invoice", example = "1")
        Long id,

        @Schema(description = "Externally generated invoice identifier", example = "3f29c6b2-8b1a-4c2d-9a3e-7d9f6a2b1c44")
        String invoiceId,

        @Schema(description = "Identifier of the billed customer", example = "1")
        String customerId,

        @Schema(description = "First day of the billing period", example = "2026-06-01")
        String billingStartDate,

        @Schema(description = "Last day of the billing period", example = "2026-06-30")
        String billingEndDate,

        @Schema(description = "Total invoice amount", example = "199.90")
        BigDecimal monetaryAmountValue,

        @Schema(description = "ISO currency code", example = "PEN")
        String monetaryAmountCurrency,

        @Schema(description = "Invoice status", example = "PENDING")
        String status,

        @Schema(description = "Optional free-text note about the invoice", example = "Monthly subscription")
        String memo
) {
}