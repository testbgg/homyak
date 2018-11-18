package com.bgdevs.madness.service.invoice;

import com.bgdevs.madness.dao.entities.card.Card;
import com.bgdevs.madness.dao.entities.card.CardType;
import com.bgdevs.madness.dao.entities.invoice.CurrencyType;
import com.bgdevs.madness.dao.entities.invoice.Invoice;
import com.bgdevs.madness.dao.exceptions.ElementNotFoundException;
import com.bgdevs.madness.dao.repositories.InvoiceRepository;
import com.bgdevs.madness.service.card.model.CardModel;
import com.bgdevs.madness.service.card.model.CardModelMapper;
import com.bgdevs.madness.service.invoice.model.CreateInvoiceModel;
import com.bgdevs.madness.service.invoice.model.InvoiceModel;
import com.bgdevs.madness.service.invoice.model.InvoiceModelMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;

import static com.bgdevs.madness.service.invoice.model.InvoiceModelMapper.toModel;
import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

/**
 * @author Nikita Shaldenkov
 */
@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

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
                .orElseThrow(() -> new ElementNotFoundException("Unable to find invoice with id:" + invoiceId));
    }

    @Transactional(readOnly = true)
    public List<CardModel> findCardsByType(long invoiceId, @Nullable CardType type) {
        return this.invoiceRepository.findById(invoiceId)
                .map(Invoice::getCards)
                .orElseThrow(() -> new ElementNotFoundException("Unable to find invoice with id:" + invoiceId))
                .stream()
                .filter(filterByType(type))
                .map(CardModelMapper::toModel)
                .collect(toList());
    }

    private Predicate<Card> filterByType(@Nullable CardType type) {
        return (type == null) ? card -> true : card -> card.getType().equals(type);
    }

    @Transactional
    public InvoiceModel create(@Nonnull CreateInvoiceModel invoice, @Nonnull Long ownerId) {
        Invoice created = this.invoiceRepository.save(toEntity(invoice, ownerId));
        return toModel(created);
    }

    @Transactional
    public void moveCashBetweenInvoices(@Nonnull MoveCashBetweenInvoices model) {
        if (model.amount.compareTo(ZERO) < 0) {
            throw new IllegalStateException("Unable to perform negative money transfer between invoices.");
        }
        Invoice fromInvoice = this.invoiceRepository.findById(model.fromInvoiceId)
                .orElseThrow(() -> new ElementNotFoundException("Unable to find invoice with id: " + model.fromInvoiceId));
        Invoice toInvoice = this.invoiceRepository.findById(model.toInvoiceId)
                .orElseThrow(() -> new ElementNotFoundException("Unable to find invoice with id: " + model.toInvoiceId));
        fromInvoice.decreaseCash(model.amount);
        toInvoice.increaseCash(model.amount);
        this.invoiceRepository.save(fromInvoice);
        this.invoiceRepository.save(toInvoice);
    }

    @Transactional
    public void markAsCard(List<Long> invoiceIds) {
        invoiceIds.forEach(id -> {
            Invoice invoice = this.invoiceRepository.findById(id)
                    .orElseThrow(() -> new ElementNotFoundException("Unable to find invoice with id:" + id));
            invoice.markAsCarded();
            this.invoiceRepository.save(invoice);
        });
    }

    private Invoice toEntity(@Nonnull CreateInvoiceModel invoice, @Nonnull Long ownerId) {

        return new Invoice(ownerId, ZERO, CurrencyType.valueOf(invoice.getCurrencyType()));
    }

    @Data
    public static class MoveCashBetweenInvoices {

        @NotNull
        private BigDecimal amount;

        @NotNull
        private Long fromInvoiceId;

        @NotNull
        private Long toInvoiceId;

    }

}
