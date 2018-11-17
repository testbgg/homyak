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
import javax.validation.constraints.NotNull;

/**
 * @author Nikita Shaldenkov
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Card extends BaseEntity {

    @NotNull
    @Column(unique = true)
    private String number;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CardType type;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CardStatus status = CardStatus.IN_PROGRESS;

    @ManyToOne
    @Nullable
    private Employee owner;

    @ManyToOne
    @NotNull
    private Invoice invoice;

    @Nullable
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "moneyLimit", column = @Column(name = "day_limit_money")),
            @AttributeOverride(name = "refreshIn", column = @Column(name = "day_limit_refresh_in"))}
    )
    private Limit dayLimit;

    @Nullable
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "moneyLimit", column = @Column(name = "month_limit_money")),
            @AttributeOverride(name = "refreshIn", column = @Column(name = "month_limit_refresh_in"))}
    )
    private Limit monthLimit;

    public Card(@Nonnull String number, @Nonnull CardType type, @Nonnull Employee employee,
                @Nonnull Invoice invoice) {
        this.number = number;
        this.type = type;
        this.owner = employee;
        this.invoice = invoice;
    }

}
