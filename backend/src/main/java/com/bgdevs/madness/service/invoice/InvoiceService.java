package com.bgdevs.madness.service.invoice;

import com.bgdevs.madness.dao.entities.invoice.Invoice;
import com.bgdevs.madness.dao.repositories.InvoiceRepository;
import com.bgdevs.madness.service.exceptions.ElementNotFoundException;
import com.bgdevs.madness.service.invoice.model.CreateInvoiceModel;
import com.bgdevs.madness.service.invoice.model.InvoiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Nikita Shaldenkov
 */
@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Page<InvoiceModel> findAll(Pageable pageable, long ownerId) {
        return this.invoiceRepository.findAllByOwnerId(ownerId, pageable)
                .map(this::toModel);
    }

    //todo
    private InvoiceModel toModel(Invoice invoice) {
        return null;
    }

    public InvoiceModel findOne(long invoiceId) {
        return this.invoiceRepository.findById(invoiceId)
                .map(this::toModel)
                .orElseThrow(() -> new ElementNotFoundException(invoiceId));
    }

    //todo add unique number check
    public InvoiceModel create(CreateInvoiceModel invoice) {
        Invoice created = this.invoiceRepository.save(toEntity(invoice));
        return toModel(created);
    }

    //todo
    private Invoice toEntity(CreateInvoiceModel invoice) {
        return null;
    }
}
