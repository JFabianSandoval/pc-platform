package com.nombrenegocio.pc211834u20221a132.billing.interfaces.rest.transform;

import com.nombrenegocio.pc211834u20221a132.billing.domain.model.aggregates.Invoice;
import com.nombrenegocio.pc211834u20221a132.billing.interfaces.rest.resources.InvoiceResource;

/**
 * Assembler to convert an Invoice aggregate to an InvoiceResource.
 *
 * <p>
 * This is where the domain's Value Objects (ChargebeeIdentifier, CustomerId,
 * BillingPeriod, MonetaryAmount) are flattened into the plain fields required
 * by the API response contract.
 * </p>
 */
public class InvoiceResourceFromEntityAssembler {

    /**
     * Converts an Invoice aggregate to an InvoiceResource.
     * @param entity The {@link Invoice} aggregate to convert.
     * @return The {@link InvoiceResource} resource.
     */
    public static InvoiceResource toResourceFromEntity(Invoice entity) {
        return new InvoiceResource(
                entity.getId(),
                entity.getInvoiceIdentifier().identifier().toString(),
                entity.getCustomerId().value().toString(),
                entity.getBillingPeriod().startDate().toString(),
                entity.getBillingPeriod().endDate().toString(),
                entity.getTotalAmount().amount(),
                entity.getTotalAmount().currency(),
                entity.getStatus().name(),
                entity.getMemo());
    }
}