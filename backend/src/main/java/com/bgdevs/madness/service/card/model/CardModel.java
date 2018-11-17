package com.bgdevs.madness.service.card.model;

import com.bgdevs.madness.dao.entities.card.CardType;
import com.bgdevs.madness.dao.entities.invoice.CurrencyType;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * @author Nikita Shaldenkov
 */
@Value
public class CardModel {

    private long id;

    private String number;

    private CardType type;

    private CardOwnerModel owner;

    private CardInvoiceModel invoice;

    private CardLimitModel dayLimit;

    private CardLimitModel monthLimit;

    @Value
    static class CardOwnerModel {

        private long id;

        private String firstName;

        private String secondName;

    }

    @Value
    static class CardInvoiceModel {

        private long id;

        private String number;

        private CurrencyType currencyType;

    }

    @Value
    static class CardLimitModel {

        private long moneyLimit;

        private LocalDateTime refreshIn;

    }
}
