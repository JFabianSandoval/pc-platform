package com.nombrenegocio.pc211834u20221a132.billing.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Resource for creating an invoice.
 */
@Schema(
        name = "CreateInvoiceRequest",
        description = "Request payload for creating a new invoice",
        example = "{\"invoiceIdentifier\": \"3f29c6b2-8b1a-4c2d-9a3e-7d9f6a2b1c44\", \"customerId\": 1, " +
                "\"billingStartDate\": \"2026-06-01\", \"billingEndDate\": \"2026-06-30\", " +
                "\"totalAmountValue\": 199.90, \"totalAmountCurrency\": \"PEN\", \"status\": \"PENDING\", \"memo\": \"Monthly subscription\"}"
)
public record CreateInvoiceResource(
        @NotNull(message = "{validation.not-null}")
        @Schema(description = "Externally generated invoice identifier (UUID), issued by the procurement bounded context",
                example = "3f29c6b2-8b1a-4c2d-9a3e-7d9f6a2b1c44")
        UUID invoiceIdentifier,

        @NotNull(message = "{validation.not-null}")
        @Positive(message = "{validation.positive}")
        @Schema(description = "Identifier of the billed customer", example = "1")
        Long customerId,

        @NotNull(message = "{validation.not-null}")
        @Schema(description = "First day of the billing period", example = "2026-06-01")
        LocalDate billingStartDate,

        @NotNull(message = "{validation.not-null}")
        @Schema(description = "Last day of the billing period", example = "2026-06-30")
        LocalDate billingEndDate,

        @NotNull(message = "{validation.not-null}")
        @DecimalMin(value = "0.0", message = "{validation.positive}")
        @Schema(description = "Total invoice amount", example = "199.90")
        BigDecimal totalAmountValue,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "ISO currency code", example = "PEN", minLength = 3, maxLength = 3)
        String totalAmountCurrency,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Invoice status", example = "PENDING")
        String status,

        @Schema(description = "Optional free-text note about the invoice", example = "Monthly subscription")
        String memo
) {
}