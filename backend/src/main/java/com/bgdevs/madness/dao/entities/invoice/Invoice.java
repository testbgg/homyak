package com.bgdevs.madness.dao.entities.invoice;

import com.bgdevs.madness.dao.entities.BaseEntity;
import com.bgdevs.madness.dao.entities.card.Card;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    public Invoice(Long ownerId, String number, BigDecimal cash, CurrencyType currencyType) {
        this.ownerId = ownerId;
        this.number = number;
        this.cash = cash;
        this.currencyType = currencyType;
    }
}
