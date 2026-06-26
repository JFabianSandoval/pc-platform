package com.nombrenegocio.pc211834u20221a132.billing.infrastructure.persistence.jpa.entities;

import com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects.NombreProyectoIdentifier;
import com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects.CustomerId;
import com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects.InvoiceStatus;
import com.nombrenegocio.pc211834u20221a132.billing.infrastructure.persistence.jpa.converters.NombreProyectoIdentifierPersistenceConverter;
import com.nombrenegocio.pc211834u20221a132.billing.infrastructure.persistence.jpa.converters.CustomerIdPersistenceConverter;
import com.nombrenegocio.pc211834u20221a132.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for invoices.
 *
 * <p>
 * Maps the Invoice aggregate to the "invoices" table. The identity, createdAt
 * and updatedAt columns are inherited from {@link AuditableAbstractPersistenceEntity}.
 * </p>
 */
@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
public class InvoicePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = NombreProyectoIdentifierPersistenceConverter.class)
    @Column(name = "invoice_identifier", nullable = false, unique = true)
    private NombreProyectoIdentifier invoiceIdentifier;

    @Convert(converter = CustomerIdPersistenceConverter.class)
    @Column(name = "customer_id", nullable = false)
    private CustomerId customerId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "startDate", column = @Column(name = "billing_start_date", nullable = false)),
            @AttributeOverride(name = "endDate", column = @Column(name = "billing_end_date", nullable = false))
    })
    private com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects.BillingPeriod billingPeriod;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "total_amount_value", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "total_amount_currency", nullable = false, length = 3))
    })
    private com.nombrenegocio.pc211834u20221a132.billing.domain.model.valueobjects.MonetaryAmount totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private InvoiceStatus status;

    @Column(name = "memo")
    private String memo;
}
 