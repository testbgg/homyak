package com.bgdevs.madness.service.card;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
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
    public CardModel create(@Nonnull CreateCardModel model) {
        Employee employee = this.employeeRepository.findById(model.getOwnerId())
                .orElseThrow(() -> new ElementNotFoundException("Unable to find employee with id: " + model.getOwnerId()));
        Invoice invoice = this.invoiceRepository.findById(model.getInvoiceId())
                .orElseThrow(() -> new ElementNotFoundException("Unable to find invoice with id: " + model.getInvoiceId()));
        Card saved = this.cardRepository.save(buildCard(model, employee, invoice));
        return toModel(saved);
    }

    private Card buildCard(@Nonnull CreateCardModel model, @Nonnull Employee employee, @Nonnull Invoice invoice) {
        CardType type = CardType.of(model.getType());
        if (type == null) {
            throw new IllegalStateException("Invalid card type: " + type);
        }
        return new Card(UUID.randomUUID().toString(), type, employee, invoice);
    }

}
