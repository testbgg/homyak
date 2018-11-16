package com.bgdevs.madness.dao.repositories;

import com.bgdevs.madness.dao.entities.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nikita Shaldenkov
 */
public interface CardRepository extends JpaRepository<Card, Long> {

}