package com.bgdevs.madness.controllers.data;

import com.bgdevs.madness.dao.entities.invoice.CurrencyType;
import com.bgdevs.madness.service.card.CardService;
import com.bgdevs.madness.service.card.model.CardModel;
import com.bgdevs.madness.service.card.model.CreateCardModel;
import com.bgdevs.madness.service.employee.EmployeeService;
import com.bgdevs.madness.service.employee.model.CreateEmployeeModel;
import com.bgdevs.madness.service.employee.model.EmployeeModel;
import com.bgdevs.madness.service.invoice.InvoiceService;
import com.bgdevs.madness.service.invoice.model.CreateInvoiceModel;
import com.bgdevs.madness.service.invoice.model.InvoiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import java.time.LocalDate;

import static com.bgdevs.madness.dao.entities.invoice.CurrencyType.FOREIGN;
import static com.bgdevs.madness.dao.entities.invoice.CurrencyType.LOCAL;

/**
 * @author Nikita Shaldenkov
 */
@RestController
@RequestMapping("/fill-db")
public class FillDataController {


    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity<Object> fillDatabase(@RequestParam long ownerId) {
        fillData(ownerId);
        return ResponseEntity.ok("Database successfully filled.");
    }

    private void fillData(long ownerId) {
        InvoiceModel localInvoice = createInvoice(ownerId, LOCAL);
        InvoiceModel foreignInvoice = createInvoice(ownerId, FOREIGN);
        EmployeeModel employeeModel = createEmployee();
        CardModel localCard = createCard(ownerId, localInvoice.getId());
        CardModel foreignCard = createCard(ownerId, foreignInvoice.getId());
    }

    private InvoiceModel createInvoice(long ownerId, @Nonnull CurrencyType type) {
        CreateInvoiceModel model = new CreateInvoiceModel(ownerId, type.name());
        return this.invoiceService.create(model);
    }

    private EmployeeModel createEmployee() {
        CreateEmployeeModel model = new CreateEmployeeModel("Ivan", "Ivanov", LocalDate.of(1970, 10, 10), "1122-345678");
        return this.employeeService.create(model);
    }

    private CardModel createCard(long ownerId, long invoiceId) {
        CreateCardModel model = new CreateCardModel("Debit", ownerId, invoiceId);
        return this.cardService.create(model);
    }

}
