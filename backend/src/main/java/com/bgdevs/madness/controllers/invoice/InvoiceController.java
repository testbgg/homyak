package com.bgdevs.madness.controllers.invoice;

import com.bgdevs.madness.dao.entities.card.CardType;
import com.bgdevs.madness.service.invoice.InvoiceService;
import com.bgdevs.madness.service.invoice.model.CreateInvoiceModel;
import com.bgdevs.madness.service.invoice.model.InvoiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

/**
 * @author Nikita Shaldenkov
 */
@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @InitBinder
    public void initTreatmentConverters(WebDataBinder dataBinder)
    {
        dataBinder.registerCustomEditor(CardType.class, new CardTypeConverter());
    }

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestParam long ownerId) {
        return ResponseEntity.ok(this.invoiceService.findAll(ownerId));
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<Object> findOne(@PathVariable long invoiceId) {
        return ResponseEntity.ok(this.invoiceService.findOne(invoiceId));
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CreateInvoiceModel createInvoiceModel) {
        InvoiceModel invoiceModel = this.invoiceService.create(createInvoiceModel);
        return ResponseEntity.created(URI.create("/invoices/" + invoiceModel.getId())).body(invoiceModel);
    }

    @GetMapping("/{invoiceId}/cards")
    public ResponseEntity<Object> getCardsByType(@PathVariable long invoiceId,
                                                 @RequestParam CardType type) {
        return ResponseEntity.ok(this.invoiceService.findCardsByType(invoiceId, type));
    }

    @PostMapping("/{invoiceId}/mark-as-carded")
    public ResponseEntity<Object> markInvoiceAsCard(@PathVariable long invoiceId) {
        this.invoiceService.markAsCard(invoiceId);
        return ResponseEntity.ok().build();
    }
}
