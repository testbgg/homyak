package com.bgdevs.madness.dao.entities.card;

import com.bgdevs.madness.dao.entities.BaseEntity;
import com.bgdevs.madness.dao.entities.employee.Employee;
import com.bgdevs.madness.dao.entities.invoice.Invoice;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Nikita Shaldenkov
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Card extends BaseEntity {

    private String number;

    @Enumerated(EnumType.STRING)
    private CardType type;

    @ManyToOne
    private Employee owner;

    @ManyToOne
    private Invoice invoice;

    @OneToOne(mappedBy = "card")
    private Limit dayLimit;

    @OneToOne(mappedBy = "card")
    private Limit monthLimit;

}
