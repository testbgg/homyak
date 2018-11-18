package com.bgdevs.madness.service.card.model;

import com.bgdevs.madness.dao.entities.card.Card;
import com.bgdevs.madness.dao.entities.employee.Employee;
import com.bgdevs.madness.dao.entities.invoice.Invoice;
import com.bgdevs.madness.service.card.model.CardModel.CardInvoiceModel;
import com.bgdevs.madness.service.card.model.CardModel.CardOwnerModel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Nikita Shaldenkov
 */
public class CardModelMapper {

    @Nonnull
    public static CardModel toModel(@Nonnull Card card) {

        return new CardModel(card.getId(), card.getNumber(), card.getType(),
                toOwnerModel(card.getOwner()), toInvoiceModel(card.getInvoice()),
                card.getOpenedDate(), card.getClosedDate(),
                card.getCreditLimit(), card.getDayLimit(), card.getMonthLimit());
    }

    @Nullable
    private static CardOwnerModel toOwnerModel(@Nullable Employee employee) {
        return employee == null ? null : new CardOwnerModel(employee.getId(), employee.getFirstName(), employee.getLastName());
    }

    @Nonnull
    private static CardInvoiceModel toInvoiceModel(@Nonnull Invoice invoice) {
        return new CardInvoiceModel(invoice.getOwnerId(), invoice.getNumber(), invoice.getCurrencyType());
    }

}
