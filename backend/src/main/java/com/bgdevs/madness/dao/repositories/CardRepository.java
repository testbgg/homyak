package com.bgdevs.madness.dao.repositories;

import com.bgdevs.madness.dao.entities.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Nikita Shaldenkov
 */
public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByInvoiceId(long invoiceId);

}
