package com.bgdevs.madness.dao.entities.card;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * @author Nikita Shaldenkov
 */
public enum CardType {

    DEBIT("Debit"),
    CREDIT("Credit"),
    CASH_IN_OUT("Cash in/out");

    private final String label;

    CardType(String label) {
        this.label = label;
    }

    @Nullable
    public static CardType of(@Nonnull String label)
    {
        return Arrays.stream(CardType.values())
                .filter(type -> type.label.equals(label))
                .findFirst()
                .orElse(null);
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
