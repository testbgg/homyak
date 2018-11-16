package com.bgdevs.madness.dao.entities.card;

import com.bgdevs.madness.dao.entities.BaseEntity;
import com.bgdevs.madness.dao.entities.employee.EmployeeInfo;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

/**
 * @author Nikita Shaldenkov
 */
@Entity
public class Card extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private CardType type;

    @ManyToOne
    private EmployeeInfo employeeInfo;
}
