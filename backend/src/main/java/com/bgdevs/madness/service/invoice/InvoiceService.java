package com.bgdevs.madness.service.invoice;

import com.bgdevs.madness.dao.entities.invoice.CurrencyType;
import com.bgdevs.madness.dao.entities.invoice.Invoice;
import com.bgdevs.madness.dao.repositories.InvoiceRepository;
import com.bgdevs.madness.service.exceptions.ElementNotFoundException;
import com.bgdevs.madness.service.invoice.model.CreateInvoiceModel;
import com.bgdevs.madness.service.invoice.model.InvoiceModel;
import com.bgdevs.madness.service.invoice.model.InvoiceModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

import static com.bgdevs.madness.service.invoice.model.InvoiceModelMapper.toModel;

/**
 * @author Nikita Shaldenkov
 */
@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Page<InvoiceModel> findAll(Pageable pageable, long ownerId) {
        return this.invoiceRepository.findAllByOwnerId(ownerId, pageable)
                .map(InvoiceModelMapper::toModel);
    }


    public InvoiceModel findOne(long invoiceId) {
        return this.invoiceRepository.findById(invoiceId)
                .map(InvoiceModelMapper::toModel)
                .orElseThrow(() -> new ElementNotFoundException(invoiceId));
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
}
