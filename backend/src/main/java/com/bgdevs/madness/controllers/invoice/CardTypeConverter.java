package com.bgdevs.madness.controllers.invoice;

import com.bgdevs.madness.dao.entities.card.CardType;

import javax.annotation.Nonnull;
import java.beans.PropertyEditorSupport;

public class CardTypeConverter extends PropertyEditorSupport {

    @Override
    public void setAsText(@Nonnull String text) {
        if (!text.isEmpty()) {
            CardType type = CardType.of(text);
            if (type == null) {
                throw new IllegalArgumentException("Incorrect card type: " + text);
            }
            setValue(type);
        } else {
            setValue(null);
        }
    }

}
