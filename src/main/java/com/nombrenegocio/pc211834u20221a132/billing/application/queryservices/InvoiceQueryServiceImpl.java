package com.nombrenegocio.pc211834u20221a132.billing.application.queryservices;

import com.nombrenegocio.pc211834u20221a132.billing.application.queries.GetAllInvoicesQuery;
import com.nombrenegocio.pc211834u20221a132.billing.application.queries.GetInvoiceByIdQuery;
import com.nombrenegocio.pc211834u20221a132.billing.application.queries.GetInvoiceByInvoiceIdentifierQuery;
import com.nombrenegocio.pc211834u20221a132.billing.application.queries.GetInvoicesByCustomerIdQuery;
import com.nombrenegocio.pc211834u20221a132.billing.application.queryservices.InvoiceQueryService;
import com.nombrenegocio.pc211834u20221a132.billing.domain.model.aggregates.Invoice;
import com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects.NombreProyectoIdentifier;
import com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects.CustomerId;
import com.nombrenegocio.pc211834u20221a132.billing.domain.repositories.InvoiceRepository;
import com.nombrenegocio.pc211834u20221a132.shared.application.result.ApplicationError;
import com.nombrenegocio.pc211834u20221a132.shared.application.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Invoice Query Service Implementation.
 */
@Service
public class InvoiceQueryServiceImpl implements InvoiceQueryService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceQueryServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Result<Invoice, ApplicationError> handle(GetInvoiceByIdQuery query) {
        return invoiceRepository.findById(query.id())
                .map(Result::<Invoice, ApplicationError>success)
                .orElseGet(() -> Result.failure(
                        ApplicationError.notFound("Invoice", String.valueOf(query.id()))));
    }

    @Override
    public Result<Invoice, ApplicationError> handle(GetInvoiceByInvoiceIdentifierQuery query) {
        try {
            var invoiceIdentifier = new NombreProyectoIdentifier(query.invoiceIdentifier());
            return invoiceRepository.findByInvoiceIdentifier(invoiceIdentifier)
                    .map(Result::<Invoice, ApplicationError>success)
                    .orElseGet(() -> Result.failure(
                            ApplicationError.notFound("Invoice", String.valueOf(query.invoiceIdentifier()))));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Invoice", e.getMessage()));
        }
    }

    @Override
    public List<Invoice> handle(GetAllInvoicesQuery query) {
        return invoiceRepository.findAll();
    }

    @Override
    public List<Invoice> handle(GetInvoicesByCustomerIdQuery query) {
        var customerId = new CustomerId(query.customerId());
        return invoiceRepository.findByCustomerId(customerId);
    }
}