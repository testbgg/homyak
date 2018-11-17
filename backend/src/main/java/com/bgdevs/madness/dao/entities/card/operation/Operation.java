package com.bgdevs.madness.dao.entities.card.operation;

import com.bgdevs.madness.dao.entities.BaseEntity;
import com.bgdevs.madness.dao.entities.card.Card;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.bgdevs.madness.dao.entities.card.operation.OperationType.CREDIT;
import static com.bgdevs.madness.dao.entities.card.operation.OperationType.DEBIT;

/**
 * @author Valeriy Knyazhev <valeriy.knayzhev@yandex.ru>
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Operation extends BaseEntity {

    @NotNull
    private BigDecimal cash;

    @NotNull
    private OperationType type;

    @NotNull
    private LocalDateTime operationDate;

    @NotNull
    private String description;

    @ManyToOne
    @NotNull
    private Card card;

    private Operation(@Nonnull BigDecimal cash, @Nonnull OperationType type, @Nonnull String description,
                      @Nonnull Card card) {
        this.cash = cash;
        this.type = type;
        this.operationDate = LocalDateTime.now();
        this.description = description;
        this.card = card;
    }

    public static Operation executeCallOperation(@Nonnull BigDecimal cash, @Nonnull String description,
                                                 @Nonnull Card card) {
        return new Operation(cash, CREDIT, description, card);
    }

    public static Operation executePutOperation(@Nonnull BigDecimal cash, @Nonnull String description,
                                                @Nonnull Card card) {
        return new Operation(cash, DEBIT, description, card);
    }

}
