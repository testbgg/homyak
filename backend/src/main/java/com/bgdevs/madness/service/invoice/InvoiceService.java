package com.bgdevs.madness.service.invoice;

import com.bgdevs.madness.dao.entities.card.Card;
import com.bgdevs.madness.dao.entities.card.CardType;
import com.bgdevs.madness.dao.entities.invoice.CurrencyType;
import com.bgdevs.madness.dao.entities.invoice.Invoice;
import com.bgdevs.madness.dao.repositories.InvoiceRepository;
import com.bgdevs.madness.service.card.model.CardModel;
import com.bgdevs.madness.service.card.model.CardModelMapper;
import com.bgdevs.madness.service.exceptions.ElementNotFoundException;
import com.bgdevs.madness.service.invoice.model.CreateInvoiceModel;
import com.bgdevs.madness.service.invoice.model.InvoiceModel;
import com.bgdevs.madness.service.invoice.model.InvoiceModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.bgdevs.madness.service.invoice.model.InvoiceModelMapper.toModel;
import static java.util.stream.Collectors.toList;

/**
 * @author Nikita Shaldenkov
 */
@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Nonnull
    private static List<CardModel> convertCards(@Nonnull List<Card> cards) {
        return cards.stream()
                .map(CardModelMapper::toModel)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<InvoiceModel> findAll(long ownerId) {
        return this.invoiceRepository.findAllByOwnerId(ownerId).stream()
                .map(InvoiceModelMapper::toModel)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public InvoiceModel findOne(long invoiceId) {
        return this.invoiceRepository.findById(invoiceId)
                .map(InvoiceModelMapper::toModel)
                .orElseThrow(() -> new ElementNotFoundException(invoiceId));
    }

    @Transactional(readOnly = true)
    public List<CardModel> findCardsByType(long invoiceId, @Nonnull CardType type) {
        return this.invoiceRepository.findById(invoiceId)
                .map(Invoice::getCards)
                .orElseThrow(() -> new ElementNotFoundException("Unable to find invoice with id:" + invoiceId))
                .stream()
                .filter(card -> card.getType().equals(type))
                .map(CardModelMapper::toModel)
                .collect(toList());
    }

    @Transactional
    public InvoiceModel create(CreateInvoiceModel invoice, Long ownerId) {
        Invoice created = this.invoiceRepository.save(toEntity(invoice, ownerId));
        return toModel(created);
    }

    private Invoice toEntity(CreateInvoiceModel invoice, Long ownerId) {

        return new Invoice(ownerId,
                UUID.randomUUID().toString(),
                BigDecimal.ZERO,
                CurrencyType.valueOf(invoice.getCurrencyType()));
    }

    @Transactional
    public void markAsCard(long invoiceId) {
        this.invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ElementNotFoundException(invoiceId))
                .setCard(true);
    }

}
