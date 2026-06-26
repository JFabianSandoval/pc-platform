package com.nombrenegocio.pc211834u20221a132.billing.interfaces.rest;

import com.nombrenegocio.pc211834u20221a132.billing.application.commandservices.InvoiceCommandService;
import com.nombrenegocio.pc211834u20221a132.billing.application.queries.GetAllInvoicesQuery;
import com.nombrenegocio.pc211834u20221a132.billing.application.queries.GetInvoiceByIdQuery;
import com.nombrenegocio.pc211834u20221a132.billing.application.queryservices.InvoiceQueryService;
import com.nombrenegocio.pc211834u20221a132.billing.interfaces.rest.resources.CreateInvoiceResource;
import com.nombrenegocio.pc211834u20221a132.billing.interfaces.rest.resources.InvoiceResource;
import com.nombrenegocio.pc211834u20221a132.billing.interfaces.rest.transform.CreateInvoiceCommandFromResourceAssembler;
import com.nombrenegocio.pc211834u20221a132.billing.interfaces.rest.transform.InvoiceResourceFromEntityAssembler;
import com.nombrenegocio.pc211834u20221a132.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * REST controller that exposes invoice resources and invoice retrieval endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/invoices", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Invoices", description = "Invoice management endpoints")
public class InvoicesController {

    private final InvoiceCommandService invoiceCommandService;
    private final InvoiceQueryService invoiceQueryService;

    public InvoicesController(InvoiceCommandService invoiceCommandService, InvoiceQueryService invoiceQueryService) {
        this.invoiceCommandService = invoiceCommandService;
        this.invoiceQueryService = invoiceQueryService;
    }

    /**
     * Create a new invoice.
     * @param resource The {@link CreateInvoiceResource} instance
     * @return An {@link InvoiceResource} resource for the created invoice
     */
    @PostMapping
    @Operation(
            summary = "Create a new invoice",
            description = "Creates a new invoice for a customer, covering a billing period and a total amount."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Invoice created successfully",
                    content = @Content(schema = @Schema(implementation = InvoiceResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Conflict - invoice already exists")
    })
    public ResponseEntity<?> createInvoice(@Valid @RequestBody CreateInvoiceResource resource) {
        var createInvoiceCommand = CreateInvoiceCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = invoiceCommandService.handle(createInvoiceCommand);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                InvoiceResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    /**
     * Get an invoice by its technical id.
     * @param invoiceId The invoice technical identifier
     * @return An {@link InvoiceResource} resource for the invoice
     */
    @GetMapping("/{invoiceId}")
    @Operation(
            summary = "Get invoice by id",
            description = "Retrieves a specific invoice's information by its technical identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Invoice found",
                    content = @Content(schema = @Schema(implementation = InvoiceResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Invoice not found")
    })
    public ResponseEntity<?> getInvoiceById(
            @PathVariable
            @Parameter(description = "Invoice technical identifier", example = "1", required = true)
            Long invoiceId
    ) {
        var getInvoiceByIdQuery = new GetInvoiceByIdQuery(invoiceId);
        var result = invoiceQueryService.handle(getInvoiceByIdQuery);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                InvoiceResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    /**
     * Get all invoices.
     * @return A list of {@link InvoiceResource} resources for all invoices
     */
    @GetMapping
    @Operation(
            summary = "Get all invoices",
            description = "Retrieves a list of all invoices in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Invoices found",
                    content = @Content(schema = @Schema(implementation = InvoiceResource.class))
            )
    })
    public ResponseEntity<List<InvoiceResource>> getAllInvoices() {
        var invoices = invoiceQueryService.handle(new GetAllInvoicesQuery());
        if (invoices.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        var invoiceResources = invoices.stream()
                .map(InvoiceResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(invoiceResources);
    }
}