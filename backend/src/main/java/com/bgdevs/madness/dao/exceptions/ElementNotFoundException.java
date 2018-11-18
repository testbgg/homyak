package com.bgdevs.madness.dao.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Nonnull;

/**
 * @author Nikita Shaldenkov
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ElementNotFoundException extends HomyakException {

    public ElementNotFoundException(@Nonnull String message) {
        super(message);
    }

}
