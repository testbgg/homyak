package com.bgdevs.madness.dao.repositories;

import com.bgdevs.madness.dao.entities.card.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nikita Shaldenkov
 */
public interface CardRepository extends JpaRepository<Card, Long> {

    Page<Card> findByInvoiceId(long invoiceId, Pageable pageable);
}
