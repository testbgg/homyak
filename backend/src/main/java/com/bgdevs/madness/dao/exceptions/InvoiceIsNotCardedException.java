package com.bgdevs.madness.dao.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Nonnull;

/**
 * @author Valeriy Knyazhev <valeriy.knyazhev@yandex.ru>
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvoiceIsNotCardedException extends HomyakException {

    public InvoiceIsNotCardedException(@Nonnull Long invoiceId) {
        super("Invoice with id: " + invoiceId + " is not carded.");
    }

}
