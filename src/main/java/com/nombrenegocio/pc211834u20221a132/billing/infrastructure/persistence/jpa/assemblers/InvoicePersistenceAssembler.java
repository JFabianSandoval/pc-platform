package com.nombrenegocio.pc211834u20221a132.billing.infrastructure.persistence.jpa.assemblers;

import com.nombrenegocio.pc211834u20221a132.billing.domain.model.aggregates.Invoice;
import com.nombrenegocio.pc211834u20221a132.billing.infrastructure.persistence.jpa.entities.InvoicePersistenceEntity;

/**
 * Static assembler between invoice domain and persistence representations.
 */
public final class InvoicePersistenceAssembler {

    private InvoicePersistenceAssembler() {
    }

    public static Invoice toDomainFromPersistence(InvoicePersistenceEntity entity) {
        if (entity == null) return null;
        var invoice = new Invoice();
        invoice.setId(entity.getId());
        invoice.setInvoiceIdentifier(entity.getInvoiceIdentifier());
        invoice.setCustomerId(entity.getCustomerId());
        invoice.setBillingPeriod(entity.getBillingPeriod());
        invoice.setTotalAmount(entity.getTotalAmount());
        invoice.setStatus(entity.getStatus());
        invoice.setMemo(entity.getMemo());
        return invoice;
    }

    public static InvoicePersistenceEntity toPersistenceFromDomain(Invoice invoice) {
        if (invoice == null) return null;
        var entity = new InvoicePersistenceEntity();
        // Only set ID if the invoice is being updated (has a non-null ID)
        // For new invoices, leave ID null to allow JPA to generate it
        if (invoice.getId() != null) {
            entity.setId(invoice.getId());
        }
        entity.setInvoiceIdentifier(invoice.getInvoiceIdentifier());
        entity.setCustomerId(invoice.getCustomerId());
        entity.setBillingPeriod(invoice.getBillingPeriod());
        entity.setTotalAmount(invoice.getTotalAmount());
        entity.setStatus(invoice.getStatus());
        entity.setMemo(invoice.getMemo());
        return entity;
    }
}