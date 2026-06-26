package com.nombrenegocio.pc211834u20221a132.billing.application.queryservices;

import com.nombrenegocio.pc211834u20221a132.billing.application.queries.GetAllInvoicesQuery;
import com.nombrenegocio.pc211834u20221a132.billing.application.queries.GetInvoiceByIdQuery;
import com.nombrenegocio.pc211834u20221a132.billing.application.queries.GetInvoiceByInvoiceIdentifierQuery;
import com.nombrenegocio.pc211834u20221a132.billing.application.queries.GetInvoicesByCustomerIdQuery;
import com.nombrenegocio.pc211834u20221a132.billing.domain.model.aggregates.Invoice;
import com.nombrenegocio.pc211834u20221a132.shared.application.result.ApplicationError;
import com.nombrenegocio.pc211834u20221a132.shared.application.result.Result;

import java.util.List;

/**
 * Invoice Query Service.
 */
public interface InvoiceQueryService {

    Result<Invoice, ApplicationError> handle(GetInvoiceByIdQuery query);

    Result<Invoice, ApplicationError> handle(GetInvoiceByInvoiceIdentifierQuery query);

    List<Invoice> handle(GetAllInvoicesQuery query);

    List<Invoice> handle(GetInvoicesByCustomerIdQuery query);
}