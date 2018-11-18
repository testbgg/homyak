package com.bgdevs.madness.dao.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Nonnull;

/**
 * @author Valeriy Knyazhev <valeriy.knyazhev@yandex.ru>
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class HomyakException extends RuntimeException {

    public HomyakException(@Nonnull String message) {
        super(message);
    }

}
