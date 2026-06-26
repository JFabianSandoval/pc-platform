package com.nombrenegocio.pc211834u20221a132.billing.infrastructure.persistence.jpa.adapters;

import com.nombrenegocio.pc211834u20221a132.billing.domain.model.aggregates.Invoice;
import com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects.NombreProyectoIdentifier;
import com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects.CustomerId;
import com.nombrenegocio.pc211834u20221a132.billing.domain.repositories.InvoiceRepository;
import com.nombrenegocio.pc211834u20221a132.billing.infrastructure.persistence.jpa.assemblers.InvoicePersistenceAssembler;
import com.nombrenegocio.pc211834u20221a132.billing.infrastructure.persistence.jpa.repositories.InvoicePersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository adapter that bridges the invoice domain repository port with Spring Data JPA.
 */
@Repository
public class InvoiceRepositoryImpl implements InvoiceRepository {

    private final InvoicePersistenceRepository invoicePersistenceRepository;

    public InvoiceRepositoryImpl(InvoicePersistenceRepository invoicePersistenceRepository) {
        this.invoicePersistenceRepository = invoicePersistenceRepository;
    }

    @Override
    public Optional<Invoice> findById(Long id) {
        return invoicePersistenceRepository.findById(id)
                .map(InvoicePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Invoice> findByInvoiceIdentifier(NombreProyectoIdentifier invoiceIdentifier) {
        return invoicePersistenceRepository.findByInvoiceIdentifier(invoiceIdentifier)
                .map(InvoicePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Invoice> findByCustomerId(CustomerId customerId) {
        return invoicePersistenceRepository.findByCustomerId(customerId)
                .stream()
                .map(InvoicePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Invoice> findAll() {
        return invoicePersistenceRepository.findAll()
                .stream()
                .map(InvoicePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Invoice save(Invoice invoice) {
        var saved = invoicePersistenceRepository.save(InvoicePersistenceAssembler.toPersistenceFromDomain(invoice));
        return InvoicePersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public boolean existsByInvoiceIdentifier(NombreProyectoIdentifier invoiceIdentifier) {
        return invoicePersistenceRepository.existsByInvoiceIdentifier(invoiceIdentifier);
    }
}
