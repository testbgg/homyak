package com.bgdevs.madness.service.card.model;

import com.bgdevs.madness.dao.entities.card.CardType;
import com.bgdevs.madness.dao.entities.invoice.CurrencyType;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Nikita Shaldenkov
 */
@Value
public class CardModel {

    private long id;

    private String number;

    private CardType type;

    @Nullable
    private CardOwnerModel owner;

    private CardInvoiceModel invoice;

    @Nullable
    private LocalDateTime openedDate;

    @Nullable
    private LocalDateTime closedDate;

    @Nullable
    private BigDecimal creditLimit;

    @Nullable
    private BigDecimal dayLimit;

    @Nullable
    private BigDecimal monthLimit;

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


}
