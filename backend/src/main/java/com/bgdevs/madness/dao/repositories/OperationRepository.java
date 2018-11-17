package com.bgdevs.madness.dao.repositories;

import com.bgdevs.madness.dao.entities.card.operation.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Valeriy Knyazhev <valeriy.knyazhev@yandex.ru>
 */
public interface OperationRepository extends JpaRepository<Operation, Long> {

    List<Operation> findByCardId(long cardId);

}
