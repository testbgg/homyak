package com.bgdevs.madness.controllers.card;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Nikita Shaldenkov
 */
@Data
public class LimitModel {

    private LocalDateTime refreshIn;

    private BigDecimal moneyLimit;

    // month \ day
    private String type;
}
