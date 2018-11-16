package com.bgdevs.madness.controllers.invoice;

import com.bgdevs.madness.service.invoice.InvoiceService;
import com.bgdevs.madness.service.invoice.model.CreateInvoiceModel;
import com.bgdevs.madness.service.invoice.model.InvoiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<?> findAll(@PageableDefault Pageable pageable,
                                     @RequestParam long ownerId) {
        return ResponseEntity.ok(this.invoiceService.findAll(pageable, ownerId));
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<?> findOne(@PathVariable long invoiceId) {
        return ResponseEntity.ok(this.invoiceService.findOne(invoiceId));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateInvoiceModel createInvoiceModel) {
        InvoiceModel invoiceModel = this.invoiceService.create(createInvoiceModel);
        return ResponseEntity.created(URI.create("/invoices/" + invoiceModel.getId())).body(invoiceModel);
    }

    @PutMapping("/{invoiceId}")
    public ResponseEntity<?> markInvoiceAsCard(@PathVariable long invoiceId) {
        this.invoiceService.markAsCard(invoiceId);
        return ResponseEntity.ok().build();
    }
}
