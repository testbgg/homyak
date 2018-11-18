package com.bgdevs.madness.dao.exceptions;

import javax.annotation.Nonnull;

/**
 * @author avbelyaev
 */
public class CardIsBlockedException extends HomyakException {

    public CardIsBlockedException(@Nonnull Long cardId) {
        super("Unable to execute action due to blocked status of card with id: " + cardId);
    }
}
