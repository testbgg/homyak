package com.bgdevs.madness.controllers;

import com.bgdevs.madness.dao.exceptions.CardIsBlockedException;
import com.bgdevs.madness.dao.exceptions.DayLimitExceededException;
import com.bgdevs.madness.dao.exceptions.MonthLimitExceededException;
import com.bgdevs.madness.dao.exceptions.NegativeMoneyAmountException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Valeriy Knyazhev <valeriy.knyazhev@yandex.ru>
 */
@ControllerAdvice(annotations = RestController.class)
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(CardIsBlockedException.class)
    public ResponseEntity<Object> catchCardIsBlockedException(
            CardIsBlockedException ex) {
        return ResponseEntity.badRequest().body("Unable to execute action due to blocked status of card.");
    }

    @ExceptionHandler(DayLimitExceededException.class)
    public ResponseEntity<Object> catchDayLimitExceededException(
            DayLimitExceededException ex) {
        return ResponseEntity.badRequest().body("Day operations limit was exceeded.");
    }

    @ExceptionHandler(MonthLimitExceededException.class)
    public ResponseEntity<Object> catchMonthLimitExceededException(
            MonthLimitExceededException ex) {
        return ResponseEntity.badRequest().body("Month operations limit was exceeded.");
    }

    @ExceptionHandler(NegativeMoneyAmountException.class)
    public ResponseEntity<Object> catchNegativeMoneyAmountException(
            NegativeMoneyAmountException ex) {
        return ResponseEntity.badRequest().body("Unable to set negative value to money amount.");
    }

}
