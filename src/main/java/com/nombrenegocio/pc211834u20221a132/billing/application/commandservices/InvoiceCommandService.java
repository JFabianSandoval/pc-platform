package com.nombrenegocio.pc211834u20221a132.billing.application.commandservices;

import com.nombrenegocio.pc211834u20221a132.billing.application.commands.CreateInvoiceCommand;
import com.nombrenegocio.pc211834u20221a132.billing.domain.model.aggregates.Invoice;
import com.nombrenegocio.pc211834u20221a132.shared.application.result.ApplicationError;
import com.nombrenegocio.pc211834u20221a132.shared.application.result.Result;

/**
 * Invoice Command Service.
 */
public interface InvoiceCommandService {

    /**
     * Handle Create Invoice Command.
     *
     * @param command The {@link CreateInvoiceCommand} Command
     * @return A {@link Result} containing the created {@link Invoice} on success,
     *         or an {@link ApplicationError} on failure (validation or business rule violation)
     */
    Result<Invoice, ApplicationError> handle(CreateInvoiceCommand command);
}
