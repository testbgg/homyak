package com.bgdevs.madness.dao.entities.card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Nikita Shaldenkov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Limit {

    private BigDecimal moneyLimit;

    private LocalDateTime refreshIn;
}
