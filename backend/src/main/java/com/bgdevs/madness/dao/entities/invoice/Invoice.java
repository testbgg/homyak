package com.bgdevs.madness.dao.entities.invoice;

import com.bgdevs.madness.dao.entities.BaseEntity;
import com.bgdevs.madness.dao.entities.card.Card;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ZERO;

/**
 * @author Nikita Shaldenkov
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
public class Invoice extends BaseEntity {

    @NotNull
    private Long ownerId;

    @NotNull
    @Column(unique = true)
    private String number;

    @NotNull
    private BigDecimal cash;

    private boolean isCard;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CurrencyType currencyType;

    @OneToMany(mappedBy = "invoice")
    private List<Card> cards = new ArrayList<>();

    public Invoice(Long ownerId, String number, BigDecimal cash, CurrencyType currencyType) {
        this.ownerId = ownerId;
        this.number = number;
        this.cash = cash;
        this.currencyType = currencyType;
    }

    public void markAsCarded() {
        this.isCard = true;
    }

    public void increaseCash(@Nonnull BigDecimal amount) {
        if (amount.compareTo(ZERO) < 0) {
            throw new IllegalStateException("Unable to add negative money amount to invoice cash.");
        }
        this.cash = this.cash.add(amount);

    }
}
