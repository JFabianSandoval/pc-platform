package com.nombrenegocio.pc211834u20221a132.billing.application.commandservices;

import com.nombrenegocio.pc211834u20221a132.billing.application.commands.CreateInvoiceCommand;
import com.nombrenegocio.pc211834u20221a132.billing.application.commandservices.InvoiceCommandService;
import com.nombrenegocio.pc211834u20221a132.billing.domain.model.aggregates.Invoice;
import com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects.BillingPeriod;
import com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects.NombreProyectoIdentifier;
import com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects.CustomerId;
import com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects.InvoiceStatus;
import com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects.MonetaryAmount;
import com.nombrenegocio.pc211834u20221a132.billing.domain.repositories.InvoiceRepository;
import com.nombrenegocio.pc211834u20221a132.shared.application.result.ApplicationError;
import com.nombrenegocio.pc211834u20221a132.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Invoice Command Service Implementation.
 */
@Service
public class InvoiceCommandServiceImpl implements InvoiceCommandService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceCommandServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    @Transactional
    public Result<Invoice, ApplicationError> handle(CreateInvoiceCommand command) {
        try {
            var invoiceIdentifier = new NombreProyectoIdentifier(command.invoiceIdentifier());

            if (invoiceRepository.existsByInvoiceIdentifier(invoiceIdentifier)) {
                return Result.failure(ApplicationError.conflict(
                        "Invoice",
                        "An invoice with identifier '%s' already exists".formatted(command.invoiceIdentifier())));
            }

            var customerId = new CustomerId(command.customerId());
            var billingPeriod = new BillingPeriod(command.billingStartDate(), command.billingEndDate());
            var totalAmount = new MonetaryAmount(command.totalAmountValue(), command.totalAmountCurrency());
            var status = InvoiceStatus.valueOf(command.status());

            var invoice = Invoice.create(invoiceIdentifier, customerId, billingPeriod, totalAmount, status, command.memo());
            var savedInvoice = invoiceRepository.save(invoice);
            return Result.success(savedInvoice);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Invoice", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Invoice creation", e.getMessage()));
        }
    }
}
 