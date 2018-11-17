package com.bgdevs.madness.service.card;

import com.bgdevs.madness.controllers.card.LimitModel;
import com.bgdevs.madness.dao.entities.card.Card;
import com.bgdevs.madness.dao.entities.card.CardType;
import com.bgdevs.madness.dao.entities.employee.Employee;
import com.bgdevs.madness.dao.entities.invoice.Invoice;
import com.bgdevs.madness.dao.repositories.CardRepository;
import com.bgdevs.madness.dao.repositories.EmployeeRepository;
import com.bgdevs.madness.dao.repositories.InvoiceRepository;
import com.bgdevs.madness.service.card.model.CardModel;
import com.bgdevs.madness.service.card.model.CreateCardModel;
import com.bgdevs.madness.service.exceptions.ElementNotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.transaction.Transactional;
import java.util.UUID;

import static com.bgdevs.madness.service.card.model.CardModelMapper.toModel;

/**
 * @author Nikita Shaldenkov
 */
@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    @Nonnull
    @Transactional
    public CardModel requestCard(@Nonnull CreateCardModel model) {
        Employee employee = null;
        if (model.getEmployeeId() != null) {
            employee = this.employeeRepository.findById(model.getEmployeeId())
                    .orElseThrow(() -> new ElementNotFoundException("Unable to find employee with id: " + model.getEmployeeId()));
        }
        Invoice invoice = this.invoiceRepository.findById(model.getInvoiceId())
                .orElseThrow(() -> new ElementNotFoundException("Unable to find invoice with id: " + model.getInvoiceId()));
        if (!invoice.isCard())
            throw new Error();
        Card saved = this.cardRepository.save(buildCard(model, employee, invoice));
        return toModel(saved);
    }

    private Card buildCard(@Nonnull CreateCardModel cardToBeCreated, @Nullable Employee employee, @Nonnull Invoice invoice) {
        CardType type = CardType.of(cardToBeCreated.getType());
        if (type == null) {
            throw new IllegalStateException("Invalid card type: " + type);
        }
        Card card = Card.request(UUID.randomUUID().toString(), type, employee, invoice);
        card.setMonthLimit(cardToBeCreated.getMonthLimit());
        card.setDayLimit(cardToBeCreated.getDayLimit());
        return card;
    }

    @Transactional
    public void addLimitToCard(@Nonnull Long cardId, AddLimitsModel addLimitsModel) {
        Card card = this.cardRepository.findById(cardId)
                .map(c -> {
                    if (addLimitsModel.dayLimit != null)
                        c.setDayLimit(addLimitsModel.dayLimit.getMoneyLimit());
                    if (addLimitsModel.monthLimit != null)
                        c.setMonthLimit(addLimitsModel.monthLimit.getMoneyLimit());
                    return c;
                })
                .orElseThrow(() -> new ElementNotFoundException(cardId));
        this.cardRepository.save(card);
    }

    @Transactional
    public void activateCard(@Nonnull Long cardId) {
        Card card = this.cardRepository.findById(cardId)
                .orElseThrow(() -> new ElementNotFoundException(cardId));
        card.activate();
        this.cardRepository.save(card);
    }

    @Transactional
    public void blockCard(@Nonnull Long cardId) {
        Card card = this.cardRepository.findById(cardId)
                .orElseThrow(() -> new ElementNotFoundException(cardId));
        card.block();
        this.cardRepository.save(card);
    }

    @Transactional
    public void closeCard(@Nonnull Long cardId) {
        Card card = this.cardRepository.findById(cardId)
                .orElseThrow(() -> new ElementNotFoundException(cardId));
        card.close();
        this.cardRepository.save(card);
    }

    @Transactional
    public CardModel reissueCard(@Nonnull Long cardId) {
        Card oldCard = this.cardRepository.findById(cardId)
                .orElseThrow(() -> new ElementNotFoundException(cardId));
        oldCard.close();
        this.cardRepository.save(oldCard);
        Card newCard = Card.request(oldCard.getNumber(), oldCard.getType(), oldCard.getOwner(), oldCard.getInvoice());
        this.cardRepository.save(newCard);
        return toModel(newCard);
    }


    @Data
    public static class AddLimitsModel {

        @Nullable
        private LimitModel dayLimit;

        @Nullable
        private LimitModel monthLimit;
    }

}
