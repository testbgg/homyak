package com.bgdevs.madness.dao.entities.invoice;

import com.bgdevs.madness.dao.entities.BaseEntity;
import com.bgdevs.madness.dao.entities.User;
import com.bgdevs.madness.dao.entities.card.Card;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Nikita Shaldenkov
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
public class Invoice extends BaseEntity {

    @ManyToOne
    private User owner;

    private String number;

    private BigDecimal cash;

    private boolean isCard;

    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;

    @OneToMany(mappedBy = "invoice")
    private List<Card> cards;
}
