package com.bgdevs.madness.dao.entities.card;

import com.bgdevs.madness.dao.entities.BaseEntity;
import com.bgdevs.madness.dao.entities.card.operation.Operation;
import com.bgdevs.madness.dao.entities.employee.Employee;
import com.bgdevs.madness.dao.entities.invoice.Invoice;
import com.bgdevs.madness.dao.exceptions.CardIsBlockedException;
import com.bgdevs.madness.dao.exceptions.DayLimitExceededException;
import com.bgdevs.madness.dao.exceptions.MonthLimitExceededException;
import com.bgdevs.madness.dao.exceptions.NegativeMoneyAmountException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private BigDecimal dayLimit;

    @Nullable
    private BigDecimal monthLimit;

    @OneToMany(mappedBy = "card", fetch = FetchType.EAGER)
    private List<Operation> operations = new ArrayList<>();

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

    public void updateLimits(@Nullable BigDecimal dayLimit, @Nullable BigDecimal monthLimit) {
        if (this.state != CLOSED) {
            setDayLimit(dayLimit);
            setMonthLimit(monthLimit);
        }
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

    public Operation executeCallOperation(@Nonnull BigDecimal cash, @Nonnull String description) {
        checkCardIsBlocked();
        BigDecimal withdrawResult = this.invoice.getCash().subtract(cash);
        if (withdrawResult.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeMoneyAmountException();
        } else {
            checkLimits();
            this.invoice.setCash(withdrawResult);
        }
        return Operation.executeCallOperation(cash, description, this);
    }

    public Operation executePutOperation(@Nonnull BigDecimal cash, @Nonnull String description) {
        return Operation.executePutOperation(cash, description, this);
    }

    private void checkLimits() {
        if (dayLimitExceeded()) {
            throw new DayLimitExceededException();
        }
        if (monthLimitExceed()) {
            throw new MonthLimitExceededException();
        }
    }

    private boolean dayLimitExceeded() {
        if (this.dayLimit == null) {
            return true;
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), 0, 0);
        BigDecimal sum = this.operations.stream()
                .filter(op -> op.getOperationDate().isAfter(startDate))
                .map(op -> op.getType().calculateOperationSum(op.getCash()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.compareTo(this.dayLimit) >= 0;
    }

    private boolean monthLimitExceed() {
        if (this.monthLimit == null) {
            return true;
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = LocalDateTime.of(now.getYear(), now.getMonth(), 1, 0, 0, 0);
        BigDecimal sum = this.operations.stream()
                .filter(op -> op.getOperationDate().isAfter(startDate))
                .map(op -> op.getType().calculateOperationSum(op.getCash()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.compareTo(this.monthLimit) >= 0;
    }

    private void checkCardIsBlocked() {
        if (this.state == BLOCKED) {
            throw new CardIsBlockedException();
        }
    }

    private void checkAndUpdateOpenedDate() {
        if (this.openedDate == null) {
            this.openedDate = LocalDateTime.now();
        }
    }

}
