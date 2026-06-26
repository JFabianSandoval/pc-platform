package com.nombrenegocio.pc211834u20221a132.billing.infrastructure.persistence.jpa.repositories;

import com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects.NombreProyectoIdentifier;
import com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects.CustomerId;
import com.nombrenegocio.pc211834u20221a132.billing.infrastructure.persistence.jpa.entities.InvoicePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data repository for invoice persistence entities.
 */
@Repository
public interface InvoicePersistenceRepository extends JpaRepository<InvoicePersistenceEntity, Long> {

    Optional<InvoicePersistenceEntity> findByInvoiceIdentifier(NombreProyectoIdentifier invoiceIdentifier);

    List<InvoicePersistenceEntity> findByCustomerId(CustomerId customerId);

    boolean existsByInvoiceIdentifier(NombreProyectoIdentifier invoiceIdentifier);
}