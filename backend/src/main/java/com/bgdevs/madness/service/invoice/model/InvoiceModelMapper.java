package com.bgdevs.madness.service.invoice.model;

import com.bgdevs.madness.dao.entities.invoice.Invoice;
import com.bgdevs.madness.service.card.model.CardModelMapper;

import static java.util.stream.Collectors.toList;

/**
 * @author Nikita Shaldenkov
 */
public class InvoiceModelMapper {

    public static InvoiceModel toModel(Invoice invoice) {
        return InvoiceModel.builder()
                .id(invoice.getId())
                .cash(invoice.getCash())
                .currencyType(invoice.getCurrencyType())
                .isCard(invoice.isCard())
                .number(invoice.getNumber())
                .ownerId(invoice.getOwner().getId())
                .cards(invoice.getCards().stream()
                        .map(CardModelMapper::toModel)
                        .collect(toList()))
                .build();
    }
}
