package com.bgdevs.madness.dao.exceptions;

import javax.annotation.Nonnull;

/**
 * @author avbelyaev
 */
public class NegativeMoneyAmountException extends HomyakException {

    public NegativeMoneyAmountException(@Nonnull Long cardId) {
        super("Unable to set negative value to money amount of card with id: " + cardId);
    }
}
