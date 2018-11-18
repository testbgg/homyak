package com.bgdevs.madness.dao.entities.card;

import com.bgdevs.madness.dao.entities.BaseEntity;
import com.bgdevs.madness.dao.entities.card.operation.Operation;
import com.bgdevs.madness.dao.entities.employee.Employee;
import com.bgdevs.madness.dao.entities.invoice.Invoice;
import com.bgdevs.madness.dao.exceptions.DayLimitExceededException;
import com.bgdevs.madness.dao.exceptions.HomyakException;
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
import java.util.Random;
import java.util.stream.IntStream;

import static com.bgdevs.madness.dao.entities.card.CardState.*;
import static com.bgdevs.madness.dao.entities.card.CardType.CREDIT;
import static java.util.stream.Collectors.joining;

/**
 * @author Nikita Shaldenkov
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Card extends BaseEntity {

    private final static BigDecimal DEFAULT_CREDIT_LIMIT = BigDecimal.valueOf(50000);

    private final static BigDecimal MAXIMUM_CREDIT_LIMIT = BigDecimal.valueOf(3000000);

    @NotNull
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
    private LocalDateTime validUntil;

    @Nullable
    private LocalDateTime closedDate;

    @ManyToOne
    @Nullable
    private Employee owner;

    @ManyToOne
    @NotNull
    private Invoice invoice;

    @Nullable
    private BigDecimal creditLimit;

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
        setCreditLimit(DEFAULT_CREDIT_LIMIT);
    }

    @Nonnull
    public static Card request(@Nonnull CardType type, @Nullable Employee owner,
                               @Nonnull Invoice invoice) {
        return new Card(generateNumber(), type, owner, invoice);
    }

    @Nonnull
    public static Card reissue(@Nonnull String number, @Nonnull CardType type,
                               @Nullable Employee owner, @Nonnull Invoice invoice) {
        return new Card(number, type, owner, invoice);
    }

    @Nonnull
    private static String generateNumber() {
        return "4444-3333-" + IntStream.range(0, 2)
                .map(index -> rand4Number())
                .boxed()
                .map(String::valueOf)
                .collect(joining("-"));
    }

    private static int rand4Number() {
        return 1000 + new Random().nextInt(9000);
    }

    public void updateCreditLimit(@Nonnull BigDecimal creditLimit) {
        setCreditLimit(creditLimit);
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
        checkCardIsActive();
        BigDecimal withdrawResult = this.invoice.getCash().subtract(cash);
        if (withdrawResult.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeMoneyAmountException(getId());
        } else {
            checkLimits();
            this.invoice.setCash(withdrawResult);
        }
        return Operation.executeCallOperation(cash, description, this);
    }

    public Operation executePutOperation(@Nonnull BigDecimal cash, @Nonnull String description) {
        checkCardIsActive();
        BigDecimal withdrawResult = this.invoice.getCash().add(cash);
        this.invoice.setCash(withdrawResult);
        return Operation.executePutOperation(cash, description, this);
    }

    private void checkLimits() {
        if (dayLimitExceeded()) {
            throw new DayLimitExceededException(getId());
        }
        if (monthLimitExceed()) {
            throw new MonthLimitExceededException(getId());
        }
    }

    private boolean dayLimitExceeded() {
        if (this.dayLimit == null) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0);
        BigDecimal sum = this.operations.stream()
                .filter(op -> op.getOperationDate().isAfter(startDate))
                .map(op -> op.getType().calculateOperationSum(op.getCash()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.compareTo(this.dayLimit.negate()) <= 0;
    }

    private boolean monthLimitExceed() {
        if (this.monthLimit == null) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = LocalDateTime.of(now.getYear(), now.getMonth(), 1, 0, 0, 0);
        BigDecimal sum = this.operations.stream()
                .filter(op -> op.getOperationDate().isAfter(startDate))
                .map(op -> op.getType().calculateOperationSum(op.getCash()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.compareTo(this.monthLimit.negate()) <= 0;
    }

    private void checkCardIsActive() {
        if (this.state != ACTIVE) {
            throw new HomyakException("Unable to perform any action for not active card with id: " + getId());
        }
    }

    private void checkAndUpdateOpenedDate() {
        if (this.openedDate == null) {
            this.openedDate = LocalDateTime.now();
            this.validUntil = this.openedDate.plusYears(4);
        }
    }

    private void setCreditLimit(@Nonnull BigDecimal creditLimit) {
        if (this.type == CREDIT && creditLimit.compareTo(MAXIMUM_CREDIT_LIMIT) <= 0) {
            this.creditLimit = creditLimit;
        }
    }

}
