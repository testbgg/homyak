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

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.bgdevs.madness.service.invoice.model.InvoiceModelMapper.toModel;

/**
 * @author Nikita Shaldenkov
 */
@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<InvoiceModel> findAll(long ownerId) {
        return this.invoiceRepository.findAllByOwnerId(ownerId).stream()
                .map(InvoiceModelMapper::toModel)
                .collect(Collectors.toList());
    }

    public InvoiceModel findOne(long invoiceId) {
        return this.invoiceRepository.findById(invoiceId)
                .map(InvoiceModelMapper::toModel)
                .orElseThrow(() -> new ElementNotFoundException(invoiceId));
    }

    public List<CardModel> findCardsByType(long invoiceId, @Nonnull CardType type) {
        return this.invoiceRepository.findById(invoiceId)
                .map(Invoice::getCards)
                .map(InvoiceService::convertCards)
                .orElseThrow(() -> new IllegalStateException("Unable to find invoice with id:" + invoiceId));
    }

    //todo add unique number check
    public InvoiceModel create(CreateInvoiceModel invoice) {
        Invoice created = this.invoiceRepository.save(toEntity(invoice));
        return toModel(created);
    }

    private Invoice toEntity(CreateInvoiceModel invoice) {

        return new Invoice(invoice.getOwnerId(),
                UUID.randomUUID().toString(),
                BigDecimal.ZERO,
                CurrencyType.valueOf(invoice.getCurrencyType()));
    }

    public void markAsCard(long invoiceId) {
        this.invoiceRepository.findById(invoiceId)
                .map(i -> {
                    i.setCard(true);
                    return i;
                })
                .orElseThrow(() -> new ElementNotFoundException(invoiceId));
    }

    @Nonnull
    private static List<CardModel> convertCards(@Nonnull List<Card> cards) {
        return cards.stream()
                .map(CardModelMapper::toModel)
                .collect(Collectors.toList());
    }

}
