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
import java.time.LocalDateTime;

import static com.bgdevs.madness.dao.entities.card.CardState.*;

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
    private CardState state = CardState.REQUESTED;

    @Nullable
    private LocalDateTime openedDate;

    @Nullable
    private LocalDateTime closedDate;

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

    private Card(@Nonnull String number, @Nonnull CardType type, @Nullable Employee employee,
                 @Nonnull Invoice invoice) {
        this.number = number;
        this.type = type;
        this.owner = employee;
        this.invoice = invoice;
    }

    public static Card request(@Nonnull String number, @Nonnull CardType type, @Nullable Employee employee,
                               @Nonnull Invoice invoice) {
        return new Card(number, type, employee, invoice);
    }

    public void activate() {
        if (state.canTransitTo(ACTIVE)) {
            this.state = ACTIVE;
            checkAndUpdateOpenedDate();
        }
    }

    public void block() {
        if (state.canTransitTo(BLOCKED)) {
            this.state = BLOCKED;
            checkAndUpdateOpenedDate();
        }
    }

    public void close() {
        if (state.canTransitTo(CLOSED)) {
            this.state = CLOSED;
            this.closedDate = LocalDateTime.now();
        }
    }

    private void checkAndUpdateOpenedDate() {
        if (this.openedDate == null) {
            this.openedDate = LocalDateTime.now();
        }
    }

}
