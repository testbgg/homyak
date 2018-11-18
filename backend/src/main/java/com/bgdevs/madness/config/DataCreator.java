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

import static java.util.stream.Collectors.toList;

/**
 * @author Nikita Shaldenkov
 */
@Service
public class DataCreator implements CommandLineRunner {

    private static List<String> firstNames = Arrays.asList("Ilya", "Alexander", "Ivan", "Denis", "Oleg", "Alexey", "Valeriy", "Anton", "Nikita", "Mikhail", "Ruslan");

    private static List<String> lastNames = Arrays.asList("Vindman", "Ovchinnikov", "Ivanov", "Goncharov", "Petrov", "Novikov", "Pechenkin", "Sotin", "Polyakov");

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

    @Nonnull
    private static Employee extractRandomEmployee(@Nonnull List<Employee> employees) {
        Random random = new Random();
        int index = random.nextInt(employees.size());
        return employees.get(index);
    }

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
                BigDecimal.valueOf(5000000),
                CurrencyType.LOCAL);
        cardedInvoice.markAsCarded();
        cardedInvoice = this.invoiceRepository.save(cardedInvoice);

        List<Employee> employees = IntStream.range(0, 10)
                .boxed()
                .map(index -> createEmployee())
                .collect(toList());

        Card debitCard1 = Card.request(CardType.DEBIT, extractRandomEmployee(employees), cardedInvoice);
        debitCard1.activate();
        Card debitCard2 = Card.request(CardType.DEBIT, extractRandomEmployee(employees), cardedInvoice);
        Card debitCard3 = Card.request(CardType.DEBIT, extractRandomEmployee(employees), cardedInvoice);
        debitCard3.block();
        Card debitCard4 = Card.request(CardType.DEBIT, extractRandomEmployee(employees), cardedInvoice);
        debitCard4.close();
        Card creditCard1 = Card.request(CardType.CREDIT, extractRandomEmployee(employees), cardedInvoice);
        creditCard1.activate();
        Card creditCard2 = Card.request(CardType.CREDIT, extractRandomEmployee(employees), cardedInvoice);
        creditCard2.block();
        Card cashInOutCard = Card.request(CardType.CASH_IN_OUT, extractRandomEmployee(employees), cardedInvoice);
        cashInOutCard.activate();
        this.cardRepository.saveAll(Arrays.asList(debitCard1, debitCard2, debitCard3, debitCard4, creditCard1,
                creditCard2, cashInOutCard));
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
