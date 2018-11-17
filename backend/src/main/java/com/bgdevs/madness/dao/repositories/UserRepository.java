package com.bgdevs.madness.dao.repositories;

import com.bgdevs.madness.dao.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Nikita Shaldenkov
 */
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
