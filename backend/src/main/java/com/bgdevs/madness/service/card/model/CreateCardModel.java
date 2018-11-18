package com.bgdevs.madness.service.card.model;

import lombok.Data;

import javax.annotation.Nullable;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

import static com.bgdevs.madness.dao.entities.card.CardType.CREDIT;

/**
 * @author Nikita Shaldenkov
 */
@Data
public class CreateCardModel {

    @NotNull
    @Pattern(regexp = "Debit|Credit|Cash in/out")
    private String type;

    @Nullable
    private Long employeeId;

    @NotNull
    private long invoiceId;

    @Nullable
    private BigDecimal dayLimit;

    @Nullable
    private BigDecimal monthLimit;

    @AssertTrue(message = "Credit card must be requested for employee.")
    private boolean isValid() {
        return !this.type.equals(CREDIT.getLabel()) || this.employeeId != null;
    }

}
