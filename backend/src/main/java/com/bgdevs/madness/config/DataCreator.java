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

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * @author Nikita Shaldenkov
 */
@Service
public class DataCreator implements CommandLineRunner {

    private static List<String> firstNames = Arrays.asList("Vasiliy", "Ivan", "Petr", "Oleg", "Alexey");

    private static List<String> lastNames = Arrays.asList("Ivanov", "Goncharov", "Petrov", "Novikov");

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

        Invoice initInvoice = new Invoice(user.getId(),
                UUID.randomUUID().toString(),
                BigDecimal.ZERO,
                CurrencyType.FOREIGN);
        this.invoiceRepository.save(initInvoice);

        Invoice cardedInvoice = new Invoice(user.getId(),
                UUID.randomUUID().toString(),
                BigDecimal.valueOf(1488),
                CurrencyType.LOCAL);
        cardedInvoice.markAsCarded();
        cardedInvoice = this.invoiceRepository.save(cardedInvoice);

        Employee employee = createEmployee();
        IntStream.of(10).forEach(index -> createEmployee());

        Card debitCard = Card.request(UUID.randomUUID().toString(), CardType.DEBIT, employee, cardedInvoice);
        Card creditCard = Card.request(UUID.randomUUID().toString(), CardType.CREDIT, employee, cardedInvoice);
        Card cahInOutCard = Card.request(UUID.randomUUID().toString(), CardType.CASH_IN_OUT, employee, cardedInvoice);
        this.cardRepository.saveAll(Arrays.asList(debitCard, creditCard, cahInOutCard));
    }

    @Nonnull
    private Employee createEmployee() {
        Random random = new Random();
        Employee employee = Employee.builder()
                .firstName(firstNames.get(random.nextInt(firstNames.size())))
                .lastName(lastNames.get(random.nextInt(lastNames.size())))
                .birthdayDate(LocalDate.of(1950 + random.nextInt(55),
                        1 + random.nextInt(12),
                        1 + random.nextInt(28)))
                .passportNumber(Long.toString(1000000000 + random.nextInt()))
                .build();
        this.employeeRepository.save(employee);
        return employee;
    }


}
