package com.bgdevs.madness.dao.entities.employee;

import com.bgdevs.madness.dao.entities.BaseEntity;
import com.bgdevs.madness.dao.entities.card.Card;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Nikita Shaldenkov
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Employee extends BaseEntity {

    private String firstName;

    private String secondName;

    private LocalDate birthdayDate;

    private String passportNumber;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Card> cards;

}
