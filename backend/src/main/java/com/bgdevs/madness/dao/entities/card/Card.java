package com.bgdevs.madness.dao.entities.card;

import com.bgdevs.madness.dao.entities.BaseEntity;
import com.bgdevs.madness.dao.entities.employee.Employee;
import com.bgdevs.madness.dao.entities.invoice.Invoice;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.*;

/**
 * @author Nikita Shaldenkov
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Card extends BaseEntity {

    @Nonnull
    private String number;

    @Enumerated(EnumType.STRING)
    @Nonnull
    private CardType type;

    @ManyToOne
    @Nonnull
    private Employee owner;

    @ManyToOne
    @Nonnull
    private Invoice invoice;

    @Nullable
    @Embedded
    private Limit dayLimit;

    @Nullable
    @Embedded
    private Limit monthLimit;

    public Card(@Nonnull String number, @Nonnull CardType type, @Nonnull Employee employee,
                @Nonnull Invoice invoice) {
        this.number = number;
        this.type = type;
        this.owner = employee;
        this.invoice = invoice;
    }

}
