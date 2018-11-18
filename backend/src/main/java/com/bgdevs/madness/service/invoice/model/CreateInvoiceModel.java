package com.bgdevs.madness.service.invoice.model;

import lombok.Data;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Nikita Shaldenkov
 */
@Data
public class CreateInvoiceModel {

    @NotNull
    private String currencyType;

    @Nullable
    private BigDecimal cash;

}
