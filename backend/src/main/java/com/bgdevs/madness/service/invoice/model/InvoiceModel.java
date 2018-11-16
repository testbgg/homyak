package com.bgdevs.madness.service.invoice.model;

import com.bgdevs.madness.dao.entities.invoice.CurrencyType;
import com.bgdevs.madness.service.card.model.CardModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Nikita Shaldenkov
 */
@Data
@AllArgsConstructor
@Builder
public class InvoiceModel {

    private long id;

    private long ownerId;

    private String number;

    private BigDecimal cash;

    private boolean isCard;

    private CurrencyType currencyType;

    private List<CardModel> cards;
}
