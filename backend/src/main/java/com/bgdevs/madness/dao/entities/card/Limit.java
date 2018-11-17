package com.bgdevs.madness.dao.entities.card;

import com.bgdevs.madness.dao.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Nikita Shaldenkov
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "card_limit")
@AllArgsConstructor
@NoArgsConstructor
public class Limit extends BaseEntity {

    private BigDecimal moneyLimit;

    private LocalDateTime refreshIn;
}
