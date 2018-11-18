package com.bgdevs.madness.dao.exceptions;

import javax.annotation.Nonnull;

/**
 * @author avbelyaev
 */
public class DayLimitExceededException extends HomyakException {

    public DayLimitExceededException(@Nonnull Long cardId) {
        super("Day operations limit was exceeded for card with id: " + cardId);
    }
}
