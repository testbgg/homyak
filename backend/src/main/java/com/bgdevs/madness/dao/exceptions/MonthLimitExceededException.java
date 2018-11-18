package com.bgdevs.madness.dao.exceptions;

import javax.annotation.Nonnull;

/**
 * @author avbelyaev
 */
public class MonthLimitExceededException extends HomyakException {

    public MonthLimitExceededException(@Nonnull Long cardId) {
        super("Month operations limit was exceeded for card with id: " + cardId);
    }
}
