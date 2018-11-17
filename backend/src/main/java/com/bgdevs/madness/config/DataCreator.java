package com.bgdevs.madness.config;

import com.bgdevs.madness.dao.entities.User;
import com.bgdevs.madness.dao.entities.card.Card;
import com.bgdevs.madness.dao.entities.card.CardType;
import com.bgdevs.madness.dao.entities.employee.Employee;
import com.bgdevs.madness.dao.entities.invoice.CurrencyType;
import com.bgdevs.madness.dao.entities.invoice.Invoice;
import com.bgdevs.madness.dao.repositories.CardRepository;
import com.bgdevs.madness.dao.repositories.EmployeeRepository;
import com.bgdevs.madness.dao.repositories.InvoiceRepository;
import com.bgdevs.madness.dao.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static java.util.Collections.emptyList;

/**
 * @author Nikita Shaldenkov
 */
@Service
public class DataCreator implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        User user = new User("admin", passwordEncoder.encode("123"), null);
        user = this.userRepository.save(user);

        Invoice invoice = new Invoice(user.getId(),
                UUID.randomUUID().toString(),
                BigDecimal.ZERO,
                CurrencyType.LOCAL);
        invoice = this.invoiceRepository.save(invoice);

        Employee employee = new Employee("Vasya", "Pupkin", LocalDate.of(1998, 12, 20),
                "471232123", emptyList());
        employee = employeeRepository.save(employee);

        Card card = new Card(UUID.randomUUID().toString(),
                CardType.CREDIT, employee, invoice);
        this.cardRepository.save(card);
    }
}
