package com.bgdevs.madness.dao.repositories;

import com.bgdevs.madness.dao.entities.invoice.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nikita Shaldenkov
 */
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Page<Invoice> findAllByOwnerId(Long ownerId, Pageable pageable);


}
