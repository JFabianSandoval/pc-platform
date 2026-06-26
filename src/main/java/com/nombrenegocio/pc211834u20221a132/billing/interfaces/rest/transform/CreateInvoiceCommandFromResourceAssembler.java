package com.nombrenegocio.pc211834u20221a132.billing.interfaces.rest.transform;

import com.nombrenegocio.pc211834u20221a132.billing.application.commands.CreateInvoiceCommand;
import com.nombrenegocio.pc211834u20221a132.billing.interfaces.rest.resources.CreateInvoiceResource;

/**
 * Assembler to convert a CreateInvoiceResource to a CreateInvoiceCommand.
 */
public class CreateInvoiceCommandFromResourceAssembler {

    /**
     * Converts a CreateInvoiceResource to a CreateInvoiceCommand.
     * @param resource The {@link CreateInvoiceResource} resource to convert.
     * @return The {@link CreateInvoiceCommand} command.
     */
    public static CreateInvoiceCommand toCommandFromResource(CreateInvoiceResource resource) {
        return new CreateInvoiceCommand(
                resource.invoiceIdentifier(),
                resource.customerId(),
                resource.billingStartDate(),
                resource.billingEndDate(),
                resource.totalAmountValue(),
                resource.totalAmountCurrency(),
                resource.status(),
                resource.memo());
    }
}