package com.bgdevs.madness.dao.entities.card;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Nikita Shaldenkov <nikita.shaldenkov@bostongene.com>
 */
public enum CardType {

    DEBIT("Debit"),
    CREDIT("Credit"),
    CASH_IN_OUT("Cash in/out");

    private final String label;

    CardType(String label) {
        this.label = label;
    }


    @JsonValue
    public String getLabel() {
        return label;
    }
}
