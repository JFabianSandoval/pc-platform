package com.nombrenegocio.pc211834u20221a132.billing.domain.repositories;

import com.nombrenegocio.pc211834u20221a132.billing.domain.model.aggregates.Invoice;
import com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects.NombreProyectoIdentifier;
import com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects.CustomerId;


import java.util.List;
import java.util.Optional;

/**
 * Invoice repository port.
 *
 * <p>
 * This interface defines the domain-level contract for persisting and
 * retrieving Invoice aggregates. Its implementation lives in the
 * infrastructure layer and is unaware of by the domain layer.
 * </p>
 */
public interface InvoiceRepository {

    Optional<Invoice> findById(Long id);

    Optional<Invoice> findByInvoiceIdentifier(NombreProyectoIdentifier invoiceIdentifier);

    List<Invoice> findByCustomerId(CustomerId customerId);

    List<Invoice> findAll();

    Invoice save(Invoice invoice);

    boolean existsByInvoiceIdentifier(NombreProyectoIdentifier invoiceIdentifier);
}