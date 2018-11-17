package com.bgdevs.madness.service.invoice.model;

import lombok.Value;

import javax.validation.constraints.NotNull;

/**
 * @author Nikita Shaldenkov
 */
@Value
public class CreateInvoiceModel {

    @NotNull
    private Long ownerId;

    @NotNull
    private String currencyType;

}
