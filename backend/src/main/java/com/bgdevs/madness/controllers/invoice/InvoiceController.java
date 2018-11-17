package com.bgdevs.madness.controllers.invoice;

import com.bgdevs.madness.dao.entities.card.CardType;
import com.bgdevs.madness.service.invoice.InvoiceService;
import com.bgdevs.madness.service.invoice.model.CreateInvoiceModel;
import com.bgdevs.madness.service.invoice.model.InvoiceModel;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * @author Nikita Shaldenkov
 */
@RestController
@RequestMapping("api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @InitBinder
    public void initTreatmentConverters(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(CardType.class, new CardTypeConverter());
    }

    @GetMapping
    public ResponseEntity<Object> findAll(@Nonnull Long ownerId) {
        return ResponseEntity.ok(this.invoiceService.findAll(ownerId));
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<Object> findOne(@PathVariable long invoiceId) {
        return ResponseEntity.ok(this.invoiceService.findOne(invoiceId));
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CreateInvoiceModel createInvoiceModel, Long ownerId) {
        InvoiceModel invoiceModel = this.invoiceService.create(createInvoiceModel, ownerId);
        return ResponseEntity.created(URI.create("/invoices/" + invoiceModel.getId())).body(invoiceModel);
    }

    @GetMapping("/{invoiceId}/cards")
    public ResponseEntity<Object> getCardsByType(@PathVariable long invoiceId,
                                                 @RequestParam CardType type) {
        return ResponseEntity.ok(this.invoiceService.findCardsByType(invoiceId, type));
    }

    @PostMapping("/mark-as-carded")
    public ResponseEntity<Object> markInvoiceAsCard(@RequestBody IdsWrapper ids) {
        this.invoiceService.markAsCard(ids.getIds());
        return ResponseEntity.ok().build();
    }


    @Data
    private class IdsWrapper {
        private List<Long> ids;
    }
}
