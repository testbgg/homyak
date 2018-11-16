package com.bgdevs.madness.dao.repositories;

import com.bgdevs.madness.dao.entities.invoice.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Nikita Shaldenkov
 */
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findAllByOwnerId(Long ownerId);

}
