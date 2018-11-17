package com.bgdevs.madness.service.card.model;

import lombok.Value;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Nikita Shaldenkov
 */
@Value
public class CreateCardModel {

    @NotNull
    @Pattern(regexp = "Debit|Credit|Cash in/out")
    private String type;

    @Nullable
    private Long employeeId;

    @NotNull
    private long invoiceId;
}
