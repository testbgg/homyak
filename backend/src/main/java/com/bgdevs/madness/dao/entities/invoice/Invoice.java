package com.bgdevs.madness.dao.entities.invoice;

import com.bgdevs.madness.dao.entities.BaseEntity;
import com.bgdevs.madness.dao.entities.card.Card;
import com.bgdevs.madness.dao.exceptions.InvoiceIsNotCardedException;
import com.bgdevs.madness.dao.exceptions.UnablePerformTransferException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import javax.annotation.Nonnull;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    public Invoice(Long ownerId, BigDecimal cash, CurrencyType currencyType) {
        this.ownerId = ownerId;
        this.number = generateNumber();
        this.cash = cash;
        this.currencyType = currencyType;
    }

    public void markAsCarded() {
        this.isCard = true;
    }

    public void increaseCash(@Nonnull BigDecimal amount) {
        operationsAreAvailable();
        this.cash = this.cash.add(amount);
    }

    public void decreaseCash(@Nonnull BigDecimal amount) {
        operationsAreAvailable();
        if (this.cash.compareTo(amount) < 0) {
            throw new UnablePerformTransferException("Unable to extract " + amount + " from invoice with id: " + getId());
        }
        this.cash = this.cash.subtract(amount);
    }

    @Nonnull
    private String generateNumber() {
        return RandomStringUtils.random(10, false, true);
    }

    private void operationsAreAvailable() {
        if (!this.isCard) {
            throw new InvoiceIsNotCardedException(getId());
        }
    }

}
