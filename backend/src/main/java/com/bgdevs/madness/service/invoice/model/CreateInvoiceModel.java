package com.bgdevs.madness.service.invoice.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Nikita Shaldenkov
 */
@Data
public class CreateInvoiceModel {

    @NotNull
    private String currencyType;

}
