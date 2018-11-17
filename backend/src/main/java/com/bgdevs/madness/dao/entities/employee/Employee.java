package com.bgdevs.madness.dao.entities.employee;

import com.bgdevs.madness.dao.entities.BaseEntity;
import com.bgdevs.madness.dao.entities.card.Card;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikita Shaldenkov
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Employee extends BaseEntity {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private LocalDate birthdayDate;

    @NotNull
    private String passportNumber;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Card> cards = new ArrayList<>();

    @Builder
    private Employee(@Nonnull String firstName, @Nonnull String lastName, @Nonnull LocalDate birthdayDate,
                     @Nonnull String passportNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdayDate = birthdayDate;
        this.passportNumber = passportNumber;
    }

}
